package com.escalade.util;

import javax.servlet.http.HttpServletRequest;

import com.escalade.model.User;

public class ConnectionChecker {
	public static User getSessionUser(HttpServletRequest request) throws ConnectionException {
		User user = (User) request.getSession().getAttribute("user");
		
		if (user == null) throw new ConnectionException("Une erreur est survenue, veuillez vous reconnecter.");
		
		return user;
	}
}
