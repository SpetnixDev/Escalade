package com.escalade.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.user.UserDAO;
import com.escalade.model.User;
import com.escalade.services.ServiceException;

@Service
public class RequestUserService {
	@Autowired
	private UserDAO userDAO;
	
	public boolean isEmailAlreadyUsed(String email) throws ServiceException {
		try {
			return userDAO.isEmailAlreadyUsed(email);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public User requestUserById(int userId)  throws ServiceException {
		try {
			return userDAO.getUserById(userId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
