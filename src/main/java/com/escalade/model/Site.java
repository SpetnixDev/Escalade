package com.escalade.model;

public class Site {
	private String name;
	private Sector[] sectors;
	
	private Site(String name, Sector[] sectors) {
		this.name = name;
		this.sectors = sectors;
	}
	
	public String getName() {
		return name;
	}
	
	public Sector[] getSectors() {
		return sectors;
	}
}
