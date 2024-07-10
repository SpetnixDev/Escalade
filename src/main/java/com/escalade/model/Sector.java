package com.escalade.model;

import java.util.ArrayList;
import java.util.List;

import com.escalade.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Sector {
	private int id;
	private String name;
	private String description;
	private List<Route> routes;
	
	@JsonCreator
	public Sector(@JsonProperty("sectorName") String name, @JsonProperty("sectorDesc") String description, @JsonProperty("routes") List<Route> routes) {
		id = 0;
		this.name = name;
		this.description = description;
		this.routes = routes;
	}
	
	@Override
	public String toString() {
		return "Sector [id=" + id + ", name=" + name + ", description=" + description + ", routes=" + routes + "]";
	}
	
	public Sector(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		
		routes = new ArrayList<>();
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
	
	public List<Route> getRoutes() {
		return routes;
	}
	
	public void addRoute(Route route) {
		routes.add(route);
	}
	
	public double getLength() {
		double length = 0;
		
		for (Route route : routes) {
			length += route.getLength();
		}
		
		return length;
	}
	
	public String getFormattedLength() {
		double length = 0;
		
		for (Route route : routes) {
			length += route.getLength();
		}
		
		return StringUtils.formatLength(length);
	}
}
