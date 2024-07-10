package com.escalade.model;

import java.util.ArrayList;
import java.util.List;

import com.escalade.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Route {
	private int id;
	private String name;
	private String description;
    private List<Pitch> pitches;
    
    @JsonCreator
    public Route(@JsonProperty("routeName") String name, @JsonProperty("routeDesc") String description, @JsonProperty("pitches") List<Pitch> pitches) {
    	id = 0;
    	this.name = name;
    	this.description = description;
    	this.pitches = pitches;
    }
    
    @Override
	public String toString() {
		return "Route [id=" + id + ", name=" + name + ", description=" + description + ", pitches=" + pitches + "]";
	}

	public Route(int id, String name, String description) {
    	this.id = id;
    	this.name = name;
    	this.description = description;
    	
    	pitches = new ArrayList<>();
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
    
    public List<Pitch> getPitches() {
		return pitches;
	}

	public void setPitches(List<Pitch> pitches) {
		this.pitches = pitches;
	}
	
	public double getLength() {
		double length = 0;
		
		for (Pitch pitch : pitches) {
			length += pitch.getLength();
		}
		
		return length;
	}
	
	public String getFormattedLength() {
		double length = 0;
		
		for (Pitch pitch : pitches) {
			length += pitch.getLength();
		}
		
		return StringUtils.formatLength(length);
	}
}
