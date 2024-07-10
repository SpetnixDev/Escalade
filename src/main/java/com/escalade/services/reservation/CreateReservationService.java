package com.escalade.services.reservation;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.reservation.ReservationDAOImpl;
import com.escalade.model.Reservation;
import com.escalade.services.ServiceException;

public class CreateReservationService {
	private ReservationDAOImpl reservationDAOImpl;
	
	public CreateReservationService() {
		reservationDAOImpl = (ReservationDAOImpl) DAOFactory.getInstance().getReservationDAO();
	}
	
	public Reservation createReservation(int userId, int topoId) throws ServiceException {
		try {
			return reservationDAOImpl.createReservation(userId, topoId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
