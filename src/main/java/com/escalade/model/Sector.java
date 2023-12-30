package com.escalade.model;

import java.util.List;

public class Sector {
	private int id;
	private String name;
	private List<Route> routes;
	private int siteId;
	
	public Sector(int id, String name, List<Route> routes, int siteId) {
		this.id = id;
		this.name = name;
		this.routes = routes;
		this.siteId = siteId;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Route> getRoutes() {
		return routes;
	}
	
	public int getSiteId() {
		return siteId;
	}
}
