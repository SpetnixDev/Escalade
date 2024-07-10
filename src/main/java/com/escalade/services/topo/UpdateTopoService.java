package com.escalade.services.topo;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.topo.TopoDAOImpl;
import com.escalade.model.Topo;
import com.escalade.services.ServiceException;

public class UpdateTopoService {
	private TopoDAOImpl topoDAOImpl;
	
	public UpdateTopoService() {
		topoDAOImpl = (TopoDAOImpl) DAOFactory.getInstance().getTopoDAO();
	}
	

	public Topo updateTopoAvailability(int topoId, boolean availability) throws ServiceException {
		try {
			return topoDAOImpl.updateTopoAvailability(topoId, availability);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
}
