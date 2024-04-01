package com.escalade.model;

import java.util.List;

public class User {
	private final int id;
	private String firstName, lastName, email;
	private final boolean member;
	private List<Topo> topos;
	
	public User(int id, String firstName, String lastName, String email, boolean member) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.member = member;
	}
	
	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}

	public boolean isMember() {
		return member;
	}
	
	public List<Topo> getTopos() {
		return topos;
	}
	
	public void setTopos(List<Topo> topos) {
		this.topos = topos;
	}
}
