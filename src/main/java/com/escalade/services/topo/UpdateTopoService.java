package com.escalade.services.topo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.topo.TopoDAO;
import com.escalade.model.Topo;
import com.escalade.services.ServiceException;

@Service
public class UpdateTopoService {
	@Autowired
	private TopoDAO topoDAO;

	public Topo updateTopoAvailability(int topoId, boolean availability) throws ServiceException {
		try {
			return topoDAO.updateTopoAvailability(topoId, availability);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
}
