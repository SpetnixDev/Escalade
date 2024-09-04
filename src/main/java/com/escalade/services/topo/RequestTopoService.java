package com.escalade.services.topo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.topo.TopoDAO;
import com.escalade.model.Topo;
import com.escalade.services.ServiceException;

@Service
public class RequestTopoService {
	@Autowired
	private TopoDAO topoDAO;
	
	public ArrayList<Topo> requestTopos() throws ServiceException {
		try {
			return topoDAO.requestTopos();
		} catch(DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public ArrayList<Topo> requestTopos(String[] regions, String keywordsString) throws ServiceException {
		String[] keywords = (keywordsString.length() == 0) ? null : keywordsString.split(" ");
		
		try {
			return topoDAO.requestTopos(regions, keywords);
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
			return topoDAO.requestToposByUser(userId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public ArrayList<String> requestLocations() throws ServiceException {
		try {
			return topoDAO.requestLocations();
		} catch(DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public boolean isTopoAvailable(int topoId) throws ServiceException {
		try {
			return topoDAO.isTopoAvailable(topoId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
