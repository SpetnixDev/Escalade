package com.escalade.dao.sector;

import java.util.List;

import com.escalade.dao.DAOException;
import com.escalade.model.Sector;

public interface SectorDAO {
	int registerSector(int siteId, String name, String description) throws DAOException;
	
	List<Sector> requestSectorsBySite(int siteId) throws DAOException;
}
