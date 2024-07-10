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
	
	public ArrayList<Topo> requestTopos() throws ServiceException {
		try {
			return topoDAOImpl.requestTopos();
		} catch(DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public ArrayList<Topo> requestTopos(String[] regions, String keywordsString) throws ServiceException {
		String[] keywords = (keywordsString.length() == 0) ? null : keywordsString.split(" ");
		
		try {
			return topoDAOImpl.requestTopos(regions, keywords);
		} catch(DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
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
	
	public ArrayList<String> requestLocations() throws ServiceException {
		try {
			return topoDAOImpl.requestLocations();
		} catch(DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public boolean isTopoAvailable(int topoId) throws ServiceException {
		try {
			return topoDAOImpl.isTopoAvailable(topoId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
