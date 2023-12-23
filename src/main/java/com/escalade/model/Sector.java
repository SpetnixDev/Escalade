package com.escalade.model;

import java.util.List;

public class Sector {
	String name;
	private List<Route> routes;
	
	public Sector(String name, List<Route> routes) {
		this.name = name;
		this.routes = routes;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Route> getRoutes() {
		return routes;
	}
}
