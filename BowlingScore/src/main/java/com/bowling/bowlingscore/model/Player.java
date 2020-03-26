package com.bowling.bowlingscore.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class Player {

	@Id
	@JsonIgnore
	private int id;

	private String name;
	
	private List<Frame> frames;
	
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Frame> getFrames() {
		return frames;
	}

	public void setFrames(List<Frame> frames) {
		this.frames = frames;
	}

	public void addFrame(Frame frame) {
		if (this.frames == null) {
			this.frames = new ArrayList<Frame>();
		}
		
		this.frames.add(frame);
	}

}
