package com.escalade.services.reservation;

import java.util.List;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.reservation.ReservationDAOImpl;
import com.escalade.model.Reservation;
import com.escalade.services.ServiceException;

public class RequestReservationService {
	private ReservationDAOImpl reservationDAOImpl;
	
	public RequestReservationService() {
		reservationDAOImpl = (ReservationDAOImpl) DAOFactory.getInstance().getReservationDAO();
	}
	
	public List<Reservation> requestReservationsConcerningUser(int userId) throws ServiceException {
		try {
			return reservationDAOImpl.getReservationsConcerningUser(userId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
