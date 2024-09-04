package com.escalade.dao.reservation;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.DetailedReservation;
import com.escalade.model.Reservation;

@Repository
public interface ReservationDAO {
	Reservation createReservation(int userId, int topoId) throws DAOException;
	
	ArrayList<Reservation> getReservationsConcerningUser(int userId) throws DAOException;
	
	Reservation updateReservation(int resId, String newStatus) throws DAOException;
	
	Reservation getReservationById(int resId) throws DAOException;
	
	ArrayList<DetailedReservation> getReservationsByUser(int userId) throws DAOException;
}
