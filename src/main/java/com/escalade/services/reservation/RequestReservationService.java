package com.escalade.services.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.reservation.ReservationDAO;
import com.escalade.model.DetailedReservation;
import com.escalade.model.Reservation;
import com.escalade.services.ServiceException;

@Service
public class RequestReservationService {
	@Autowired
	private ReservationDAO reservationDAO;
	
	public List<Reservation> requestReservationsConcerningUser(int userId) throws ServiceException {
		try {
			return reservationDAO.getReservationsConcerningUser(userId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public List<DetailedReservation> requestReservationsByUser(int userId) throws ServiceException {
		try {
			return reservationDAO.getReservationsByUser(userId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
