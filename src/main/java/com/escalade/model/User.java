package com.escalade.model;

public class User {
	private int id;
	private String username;
	private Topo[] topos;
	
	public User(int id, String username, Topo[] topos) {
		this.id = id;
		this.username = username;
		this.topos = topos;
	}
	
	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
	
	public Topo[] getTopos() {
		return topos;
	}
}
