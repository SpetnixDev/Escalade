package com.escalade.services.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.reservation.ReservationDAO;
import com.escalade.model.Reservation;
import com.escalade.services.ServiceException;

@Service
public class UpdateReservationService {
	@Autowired
	private ReservationDAO reservationDAO;

	public Reservation updateReservation(int resId, String reservationStatus) throws ServiceException {
		try {
			return reservationDAO.updateReservation(resId, reservationStatus);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard");
		}
	}

}
