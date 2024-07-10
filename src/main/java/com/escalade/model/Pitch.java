package com.escalade.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pitch {
	private int id;
	private String name;
	private String description;
	private double length;
	private String difficulty;
	
	@JsonCreator
	public Pitch(@JsonProperty("pitchName") String name, @JsonProperty("pitchDesc") String description, @JsonProperty("pitchLength") double length, @JsonProperty("pitchDifficulty") String difficulty) {
		id = 0;
		this.name = name;
		this.description = description;
		this.length = length;
		this.difficulty = difficulty;
	}
	
	@Override
	public String toString() {
		return "Pitch [id=" + id + ", name=" + name + ", description=" + description + ", length=" + length
				+ ", difficulty=" + difficulty + "]";
	}

	public Pitch(int id, String name, String description, double length, String difficulty) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.length = length;
		this.difficulty = difficulty;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getLength() {
		return length;
	}

	public String getDifficulty() {
		return difficulty;
	}
}
