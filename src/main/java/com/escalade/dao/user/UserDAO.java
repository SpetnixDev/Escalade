package com.escalade.dao.user;

import com.escalade.dao.DAOException;
import com.escalade.model.User;

public interface UserDAO {
	User registerUser(String email, String firstName, String lastName, String password) throws DAOException;
	
	User authenticateUser(String email, String password) throws DAOException;
	
	//User getUserById(int id);
	
	boolean isEmailAlreadyUsed(String email) throws DAOException;
}
