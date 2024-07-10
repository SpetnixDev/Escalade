package com.escalade.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.escalade.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Site {
	private int id;
	
	@Override
	public String toString() {
		return "Site [id=" + id + ", name=" + name + ", description=" + description + ", region=" + region
				+ ", sectors=" + sectors + ", official=" + official + "]";
	}

	private String name;
	private String description;
	private String region;
	private List<Sector> sectors;
	private boolean official;
	
	@JsonCreator
	public Site(@JsonProperty("siteName") String name, @JsonProperty("location") String region, @JsonProperty("description") String description, @JsonProperty("sectors") List<Sector> sectors) {
		id = 0; 
		this.name = name;
		this.description = description;
		this.region = region;
		this.sectors = sectors;
		official = false;
	}
	
	public Site(int id, String name, String description, String region, List<Sector> sectors) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.region = region;
		this.sectors = sectors;
		
		Random random = new Random();
		
		official = random.nextBoolean();
	}

	public Site(int id, String name, String description, String region, boolean official) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.region = region;
		this.official = official;
		
		sectors = new ArrayList<>();
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
	
	public String getRegion() {
		return region;
	}
	
	public List<Sector> getSectors() {
		return sectors;
	}
	
	public boolean isOfficial() {
		return official;
	}

	public void addSector(Sector sector) {
		sectors.add(sector);
	}
	
	public String getLength() {
		double length = 0;
		
		for (Sector sector : sectors) {
			length += sector.getLength();
		}
		
		return StringUtils.formatLength(length);
	}
	
	public int getNumberOfRoutes() {
		int routes = 0;
		
		for (Sector sector : sectors) {
			routes += sector.getRoutes().size();
		}
		
		return routes;
	}
	
	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}
}
