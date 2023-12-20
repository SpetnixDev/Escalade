package com.escalade.model;

import java.util.Random;

public class Topo {
	private int id;
	private String title;
	private String description;
	private String region;
	private String owner;
	private boolean available;
	
	public Topo(int id, String title, String description, String region, String owner) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.region = region;
		this.owner = owner;
		
		Random random = new Random();
		
		available = random.nextBoolean();
	}
	
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getRegion() {
		return region;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public boolean isAvailable() {
		return available;
	}
}
