package com.escalade.services.topo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.topo.TopoDAO;
import com.escalade.services.ServiceException;

@Service
public class RemoveTopoService {
	@Autowired
	private TopoDAO topoDAO;
	
	public boolean removeTopo(int topoId) throws ServiceException {
		try {
			return topoDAO.removeTopo(topoId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
