package com.bowling.bowlingscore.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "Game")
public class Game {

	@Id
	@JsonIgnore
	private int id;
	
	private String alley;
	
	private List<Player> players;

	public Game(int id, String alley) {
		this.id = id;
		this.alley = alley;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlley() {
		return alley;
	}

	public void setAlley(String alley) {
		this.alley = alley;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void addPlayer(Player player) {
		if (this.players == null) {
			this.players = new ArrayList<Player>();
		}
		
		this.players.add(player);
	}
}
