package com.escalade.model;

import java.util.List;
import java.util.Random;

public class Site {
	private int id;
	private String name;
	private String region;
	private List<Sector> sectors;
	private boolean official;
	
	public Site(int id, String name, String region, List<Sector> sectors) {
		this.id = id;
		this.name = name;
		this.region = region;
		this.sectors = sectors;
		
		Random random = new Random();
		
		official = random.nextBoolean();
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
	
	public boolean isOfficial() {
		return official;
	}
}
