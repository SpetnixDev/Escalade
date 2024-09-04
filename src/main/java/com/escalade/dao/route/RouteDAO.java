package com.escalade.dao.route;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.Route;

@Repository
public interface RouteDAO {
	int registerRoute(int sectorId, String name, String description) throws DAOException;
	
	List<Route> requestRoutesBySector(int sectorId) throws DAOException;
}
