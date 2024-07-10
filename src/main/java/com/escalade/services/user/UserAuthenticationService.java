package com.escalade.services.user;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.user.UserDAOImpl;
import com.escalade.model.User;
import com.escalade.services.ServiceException;
import com.escalade.util.PasswordHashing;

public class UserAuthenticationService {
	private UserDAOImpl userDAOImpl;
	
	public UserAuthenticationService() {
		userDAOImpl = (UserDAOImpl) DAOFactory.getInstance().getUserDAO();
	}
	
	public User authenticateUser(String email, String password) throws ServiceException {
		password = PasswordHashing.hashPassword(password);
		
		try {
			return userDAOImpl.authenticateUser(email, password);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
