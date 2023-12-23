package com.escalade.model;

public class Sector {
	String name;
	private Route[] routes;
	
	public Sector(String name, Route[] routes) {
		this.name = name;
		this.routes = routes;
	}
	
	public String getName() {
		return name;
	}
	
	public Route[] getRoutes() {
		return routes;
	}
}
