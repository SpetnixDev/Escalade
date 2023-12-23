package com.escalade.model;

import java.util.List;

public class Route {
	private String name;
    private String difficulty;
    private List<Pitch> pitches;
    
    public Route(String name, String difficulty, List<Pitch> pitches) {
    	this.name = name;
    	this.difficulty = difficulty;
    	this.pitches = pitches;
    }
    
    public String getName() {
		return name;
	}
    
    public String getDifficulty() {
		return difficulty;
	}
    
    public List<Pitch> getPitches() {
		return pitches;
	}
}
