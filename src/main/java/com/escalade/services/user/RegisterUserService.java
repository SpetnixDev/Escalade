package com.escalade.services.user;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.user.UserDAOImpl;
import com.escalade.model.User;
import com.escalade.services.ServiceException;
import com.escalade.util.PasswordHashing;

public class RegisterUserService {
	private UserDAOImpl userDAOImpl;
	
	public RegisterUserService() {
		userDAOImpl = (UserDAOImpl) DAOFactory.getInstance().getUserDAO();
	}
	
	public User registerUser(String email, String firstName, String lastName, String password) throws ServiceException {
		try {
			password = PasswordHashing.hashPassword(password);
			
			return userDAOImpl.registerUser(email, firstName, lastName, password);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard");
		}
	}
}
