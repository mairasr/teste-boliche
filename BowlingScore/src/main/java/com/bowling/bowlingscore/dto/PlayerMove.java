package com.bowling.bowlingscore.dto;

public class PlayerMove {
	
	private String name;
	private int pins;
	private String alley;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPins() {
		return pins;
	}
	public void setPins(int pins) {
		this.pins = pins;
	}
	
	public String getAlley() {
		return alley;
	}
	public void setAlley(String alley) {
		this.alley = alley;
	}
	
}
