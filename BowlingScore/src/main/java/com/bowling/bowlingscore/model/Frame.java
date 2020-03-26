package com.bowling.bowlingscore.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class Frame {
	
	@Id
	@JsonIgnore
	private int id;

	@JsonIgnore
	private int number;
	
	private List<Integer> balls;
	
	private int score;
	
	public Frame(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<Integer> getBalls() {
		return balls;
	}

	public void setBalls(List<Integer> balls) {
		this.balls = balls;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int sumBalls() {
		if (this.balls == null)
			return 0;
		
		int sum = 0;
		for (int i : this.balls ) {
			sum = sum + i;
		}
		
		return sum;
	}

	public int sumRounds() {
		if (this.balls == null)
			return 0;
		
		return this.balls.size();
	}

	public void addBall(int pins) {
		if (this.balls == null) {
			this.balls = new ArrayList<Integer>();
		}
		this.balls.add(pins);
	}

	@JsonIgnore
	public boolean isSpare() {
		if (this.balls == null) {
			return false;
		}
		return this.sumBalls()==10;
	}

	@JsonIgnore
	public boolean isStrike() {
		if (this.balls == null) {
			return false;
		}
		return this.getBalls().get(0)==10;
	}
}
