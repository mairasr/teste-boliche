package com.bowling.bowlingscore.business;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.bowling.bowlingscore.dto.PlayerMove;
import com.bowling.bowlingscore.model.Frame;
import com.bowling.bowlingscore.model.Game;
import com.bowling.bowlingscore.model.Player;
import com.bowling.bowlingscore.repository.FrameRepository;
import com.bowling.bowlingscore.repository.GameRepository;
import com.bowling.bowlingscore.repository.PlayerRepository;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private FrameRepository frameRepository;

	@Autowired
	private SequenceGeneratorService sequenceService;

	@Autowired
	private MongoOperations mongo;

	
	public String deleteAlley(String alley) {

		gameRepository.delete(this.getGame(alley));
		return "Game deletado com sucesso!";
	}


	public Game getGame(String alley) {
		Game game = mongo.findOne(query(where("alley").is(alley)), Game.class);
		return game;
	}


	private Game addAlley(String alley) {
		
		Game game = this.getGame(alley);
		
		return (game!=null)?game:gameRepository.insert(new Game(sequenceService.getNextSequence("customSequences"), alley));
	}
	

	public void addScore(String alley, PlayerMove playerMove) {
		
		Game game = addAlley(alley);
		
		Player player = getPlayer(game, playerMove.getName());
		boolean newPlayer = (player==null);
		
		if (newPlayer) {
			player = new Player(sequenceService.getNextSequence("customSequences"),playerMove.getName());
		}

		Frame lastFrame = getLastFrame(player);

		if (playerMove.getPins() > 10) {
			throw new RuntimeException("Jogada não permitida!");
		}
		if ( (!newPlayer) && 
				lastFrame.getNumber() == 10 && 
				lastFrame.sumRounds() == 3) {
			throw new RuntimeException("Jogada não permitida!");
		}

		int points = playerMove.getPins();
		
		//Criar novo frame
		if (newPlayer || (lastFrame.getNumber()!=10 && (lastFrame.sumBalls()==10 || lastFrame.sumRounds()==2))) {
			
			Frame frame = new Frame(sequenceService.getNextSequence("customSequences"));
			
			if (!newPlayer && (lastFrame.isSpare()||lastFrame.isStrike())) {
				lastFrame.setScore(lastFrame.getScore()+points);
				frameRepository.save(lastFrame);
			}
			frame.setNumber(newPlayer ? 1 : lastFrame.getNumber()+1);
			frame.addBall(playerMove.getPins());
			frame.setScore(newPlayer ? points : lastFrame.getScore()+points);
			
			frameRepository.insert(frame);
			
			player.addFrame(frame);
			
		//Atualizar frame
		} else {

			lastFrame.addBall(playerMove.getPins());
			
			if (lastFrame.getNumber()==10 && lastFrame.sumBalls()>10) {
				throw new RuntimeException("Jogada não permitida!");
			}

			if ((lastFrame.getNumber()>1 && getFrame(player, lastFrame.getNumber()-1).isStrike()) ||
					(lastFrame.getNumber()==10 && lastFrame.sumBalls() == 10)) { //Ultima Rodada
				Frame f = getFrame(player, lastFrame.getNumber()-1);
				f.setScore(f.getScore()+points);
				frameRepository.save(f);

				lastFrame.setScore(lastFrame.getScore()+points);
			}
			
			
			lastFrame.setScore(lastFrame.getScore()+points);
			frameRepository.save(lastFrame);
			
		}
		
		
		if (newPlayer) {
			game.addPlayer(player);
		}

		playerRepository.save(player);

		gameRepository.save(game);
		
	}
	

	

	private Player getPlayer(Game game, String name) {
		if (game.getPlayers() == null) 
			return null;
		
		for(Player p : game.getPlayers()) {
			  if (name.equals(p.getName())) 
				  return p;
		}
		
		return null;
	}


	private Frame getLastFrame(Player player) {
		
		if (player.getFrames()==null)
			return null;
		
		Frame frame = player.getFrames().get(0);
		
		for (Frame f : player.getFrames()) {
			if (f.getNumber() > frame.getNumber()) {
				frame = f;
			}
		}
		
		return frame;
	}

	private Frame getFrame(Player player, int i) {
		for (Frame f : player.getFrames()) {
			if (f.getNumber() == i) {
				return f;
			}
		}
		return null;
	}




}
