package com.escalade.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.user.UserDAO;
import com.escalade.model.User;
import com.escalade.services.ServiceException;
import com.escalade.util.PasswordHashing;

@Service
public class RegisterUserService {
	@Autowired
	private UserDAO userDAO;
	
	public User registerUser(String email, String firstName, String lastName, String password) throws ServiceException {
		try {
			password = PasswordHashing.hashPassword(password);
			
			return userDAO.registerUser(email, firstName, lastName, password);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard");
		}
	}
}
