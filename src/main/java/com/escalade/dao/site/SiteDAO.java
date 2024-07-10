package com.escalade.dao.site;

import java.util.ArrayList;

import com.escalade.dao.DAOException;
import com.escalade.model.Site;

public interface SiteDAO {
	int registerSite(String name, String description, String region) throws DAOException;
	
	Site requestSiteById(int siteId) throws DAOException;
	
	ArrayList<Site> requestSites() throws DAOException;
}
