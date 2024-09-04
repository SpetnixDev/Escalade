package com.escalade.dao.sector;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.Sector;

@Repository
public interface SectorDAO {
	int registerSector(int siteId, String name, String description) throws DAOException;
	
	List<Sector> requestSectorsBySite(int siteId) throws DAOException;
	
	int getMinSectorCount() throws DAOException;
	
	int getMaxSectorCount() throws DAOException;
}
