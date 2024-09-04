package com.escalade.services.topo;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.topo.TopoDAO;
import com.escalade.services.ServiceException;

@Service
public class RegisterTopoService {
	@Autowired
	private TopoDAO topoDAO;
	
	public boolean registerNewTopo(int userId, String title, String description, String location, Date releaseDate) throws ServiceException {
		try {
			return topoDAO.registerNewTopo(userId, title, description, location, releaseDate);
		} catch(DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
