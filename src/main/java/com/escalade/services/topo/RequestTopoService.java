package com.escalade.services.topo;

import java.util.ArrayList;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.topo.TopoDAOImpl;
import com.escalade.model.Topo;
import com.escalade.services.ServiceException;

public class RequestTopoService {
	private TopoDAOImpl topoDAOImpl;
	
	public RequestTopoService() {
		topoDAOImpl = (TopoDAOImpl) DAOFactory.getInstance().getTopoDAO();
	}
	
	public ArrayList<Topo> requestTopos() {
		return null;
	}
	
	public ArrayList<Topo> requestTopos(String[] regions, String[] keywords) {
		return null;
	}
	
	public Topo requestTopoById(int id) {
		return null;
	}
	
	public ArrayList<Integer> requestReservedToposByUser(int userId) {
		return null;
	}
	
	public ArrayList<Topo> requestToposByUser(int userId) throws ServiceException {
		try {
			return topoDAOImpl.requestToposByUser(userId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
