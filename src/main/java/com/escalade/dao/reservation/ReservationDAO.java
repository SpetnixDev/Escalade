package com.escalade.dao.reservation;

import java.util.ArrayList;

import com.escalade.dao.DAOException;
import com.escalade.model.Reservation;

public interface ReservationDAO {
	Reservation createReservation(int userId, int topoId) throws DAOException;
	
	ArrayList<Reservation> getReservationsConcerningUser(int userId) throws DAOException;
	
	Reservation updateReservation(int resId, String newStatus) throws DAOException;
	
	Reservation getReservationById(int resId) throws DAOException;
}
