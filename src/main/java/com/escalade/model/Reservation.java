package com.escalade.model;

public class Reservation {
	private int id;
	private int userId;
	private int topoId;
	private String status;
	
	public Reservation(int id, int userId, int topoId, String status) {
		this.id = id;
		this.userId = userId;
		this.topoId = topoId;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public int getTopoId() {
		return topoId;
	}
	
	public String getStatus() {
		return status;
	}
}
