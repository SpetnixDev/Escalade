package com.escalade.dao.site;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.Site;

@Repository
public interface SiteDAO {
	int registerSite(String name, String description, String region, String rating) throws DAOException;
	
	Site requestSiteById(int siteId) throws DAOException;
	
	ArrayList<Site> requestSites() throws DAOException;
	
	ArrayList<Site> requestSites(String[] regions, boolean sectorsNumbersEnabled, int sectorsNumbers,
			boolean ratingEnabled, String rating, boolean official, String keywords[]) throws DAOException;
	
	ArrayList<Site> requestMostRecentSites() throws DAOException;
	
	ArrayList<String> requestRegions() throws DAOException;
	
	boolean updateOfficialStatus(int siteId) throws DAOException;
}
