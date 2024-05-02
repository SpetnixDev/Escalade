package com.escalade.dao.topo;

import java.sql.Date;
import java.util.ArrayList;

import com.escalade.dao.DAOException;
import com.escalade.model.Topo;

public interface TopoDAO {
	ArrayList<Topo> requestTopos() throws DAOException;
	
	ArrayList<Topo> requestTopos(String[] regions, String[] keywords) throws DAOException;
	
	boolean registerNewTopo(int userId, String title, String description, String location, Date releaseDate) throws DAOException;
	
	ArrayList<Topo> requestToposByUser(int userId) throws DAOException;
	
	boolean isTopoAvailable(int topoId) throws DAOException;
	
	Topo requestTopoById(int topoId) throws DAOException;
	
	boolean removeTopo(int topoId) throws DAOException;
	
	ArrayList<Integer> requestReservedToposByUser(int userId) throws DAOException;
	
	ArrayList<String> requestLocations() throws DAOException;
	
	Topo updateTopoAvailability(int topoId, boolean available) throws DAOException;
}
