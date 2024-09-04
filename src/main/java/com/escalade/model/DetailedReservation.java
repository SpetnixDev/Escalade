package com.escalade.model;

public class DetailedReservation extends Reservation {
	private Topo topo;
	private String ownerEmail;

	public DetailedReservation(int id, int userId, int topoId, String status, Topo topo, String ownerEmail) {
		super(id, userId, topoId, status);
		
		this.topo = topo;
		this.ownerEmail = ownerEmail;
	}

	public Topo getTopo() {
		return topo;
	}
	
	public String getOwnerEmail() {
		return ownerEmail;
	}
}
