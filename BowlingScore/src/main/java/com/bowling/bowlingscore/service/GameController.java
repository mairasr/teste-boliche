package com.bowling.bowlingscore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bowling.bowlingscore.business.GameService;
import com.bowling.bowlingscore.dto.PlayerMove;
import com.bowling.bowlingscore.model.Game;

@RestController
public class GameController {

	@Autowired
	private GameService gameService;
	
	@GetMapping("/game/{alley}/score")
	public Game getScore(@PathVariable String alley) {
		
		return gameService.getGame(alley);
	} 
	
	@PostMapping("/game/score")
	public void addScore(@RequestBody PlayerMove playerMove) {
		
		gameService.addScore(playerMove.getAlley(), playerMove);
	}

	@DeleteMapping("/game/{alley}")
	public String deleteAlley(@PathVariable String alley) {
		
		return gameService.deleteAlley(alley);
	}
	
}
