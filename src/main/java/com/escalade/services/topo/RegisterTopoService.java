package com.escalade.services.topo;

import java.sql.Date;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.topo.TopoDAOImpl;
import com.escalade.services.ServiceException;

public class RegisterTopoService {
	private TopoDAOImpl topoDAOImpl;
	
	public RegisterTopoService() {
		topoDAOImpl = (TopoDAOImpl) DAOFactory.getInstance().getTopoDAO();
	}
	
	public boolean registerNewTopo(int userId, String title, String description, String location, Date releaseDate) throws ServiceException {
		try {
			return topoDAOImpl.registerNewTopo(userId, title, description, location, releaseDate);
		} catch(DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
