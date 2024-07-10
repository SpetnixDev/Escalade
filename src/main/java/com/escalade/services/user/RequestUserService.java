package com.escalade.services.user;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.user.UserDAOImpl;
import com.escalade.services.ServiceException;

public class RequestUserService {
	private UserDAOImpl userDAOImpl;
	
	public RequestUserService() {
		userDAOImpl = (UserDAOImpl) DAOFactory.getInstance().getUserDAO();
	}
	
	public boolean isEmailAlreadyUsed(String email) throws ServiceException {
		try {
			return userDAOImpl.isEmailAlreadyUsed(email);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
