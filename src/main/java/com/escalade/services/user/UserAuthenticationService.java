package com.escalade.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.user.UserDAO;
import com.escalade.model.User;
import com.escalade.services.ServiceException;
import com.escalade.util.PasswordHashing;

@Service
public class UserAuthenticationService {
	@Autowired
	private UserDAO userDAO;
	
	public User authenticateUser(String email, String password) throws ServiceException {
		password = PasswordHashing.hashPassword(password);
		
		try {
			return userDAO.authenticateUser(email, password);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
