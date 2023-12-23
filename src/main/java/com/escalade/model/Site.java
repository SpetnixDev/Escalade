package com.escalade.model;

import java.util.List;

public class Site {
	private int id;
	private String name;
	private String region;
	private List<Sector> sectors;
	
	public Site(int id, String name, String region, List<Sector> sectors) {
		this.id = id;
		this.name = name;
		this.region = region;
		this.sectors = sectors;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRegion() {
		return region;
	}
	
	public List<Sector> getSectors() {
		return sectors;
	}
}
