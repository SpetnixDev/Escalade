package com.escalade.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Topo {
	private int id;
	private String title;
	private String description;
	private String location;
	private Date releaseDate;
	private int ownerId;
	private boolean available;
	
	public Topo(int id, String title, String description, String location, Date releaseDate, int ownerId, boolean available) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.releaseDate = releaseDate;
		this.ownerId = ownerId;
		this.available = available;
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
	
	public String getLocation() {
		return location;
	}

	public String getReleaseDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = format.format(releaseDate);
		
		return formattedDate;
	}
	
	public int getOwnerId() {
		return ownerId;
	}
	
	public boolean isAvailable() {
		return available;
	}
}
