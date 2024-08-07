package com.escalade.services.topo;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.topo.TopoDAOImpl;
import com.escalade.services.ServiceException;

public class RemoveTopoService {
	private TopoDAOImpl topoDAOImpl;
	
	public RemoveTopoService() {
		topoDAOImpl = (TopoDAOImpl) DAOFactory.getInstance().getTopoDAO();
	}
	
	public boolean removeTopo(int topoId) throws ServiceException {
		try {
			return topoDAOImpl.removeTopo(topoId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
