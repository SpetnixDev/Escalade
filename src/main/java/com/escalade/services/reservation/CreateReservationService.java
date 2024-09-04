package com.escalade.services.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.reservation.ReservationDAO;
import com.escalade.model.Reservation;
import com.escalade.services.ServiceException;

@Service
public class CreateReservationService {
	@Autowired
	private ReservationDAO reservationDAO;
	
	public Reservation createReservation(int userId, int topoId) throws ServiceException {
		try {
			return reservationDAO.createReservation(userId, topoId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
