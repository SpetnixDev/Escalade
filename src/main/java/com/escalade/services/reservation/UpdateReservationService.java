package com.escalade.services.reservation;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.reservation.ReservationDAOImpl;
import com.escalade.model.Reservation;
import com.escalade.services.ServiceException;

public class UpdateReservationService {
	private ReservationDAOImpl reservationDAOImpl;
	
	public UpdateReservationService() {
		reservationDAOImpl = (ReservationDAOImpl) DAOFactory.getInstance().getReservationDAO();
	}

	public Reservation updateReservation(int resId, String reservationStatus) throws ServiceException {
		try {
			return reservationDAOImpl.updateReservation(resId, reservationStatus);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard");
		}
	}

}
