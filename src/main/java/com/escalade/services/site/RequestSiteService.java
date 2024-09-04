package com.escalade.services.site;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.pitch.PitchDAO;
import com.escalade.dao.route.RouteDAO;
import com.escalade.dao.sector.SectorDAO;
import com.escalade.dao.site.SiteDAO;
import com.escalade.model.Pitch;
import com.escalade.model.Route;
import com.escalade.model.Sector;
import com.escalade.model.Site;
import com.escalade.services.ServiceException;

@Service
public class RequestSiteService {
	@Autowired
	private SiteDAO siteDAO;
	@Autowired
	private SectorDAO sectorDAO;
	@Autowired
	private RouteDAO routeDAO;
	@Autowired
	private PitchDAO pitchDAO;
	
	public Site requestSite(int siteId) throws ServiceException {
		try {
			Site site = siteDAO.requestSiteById(siteId);
			
			List<Sector> sectors = sectorDAO.requestSectorsBySite(siteId);
			
			for (Sector sector : sectors) {
				List<Route> routes = routeDAO.requestRoutesBySector(sector.getId());
				
				for (Route route : routes) {
					List<Pitch> pitches = pitchDAO.requestPitchesByRoute(route.getId());
					
					route.setPitches(pitches);
					
					sector.addRoute(route);
				}
				
				site.addSector(sector);
			}
			
			return site;
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public ArrayList<Site> requestSites() throws ServiceException {
		try {
			ArrayList<Site> sites = siteDAO.requestSites();
			
			for (Site site : sites) {
				List<Sector> sectors = sectorDAO.requestSectorsBySite(site.getId());
			
				site.setSectors(sectors);
			}
			
			return sites;
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public ArrayList<Site> requestMostRecentSites() throws ServiceException {
		try {
			ArrayList<Site> sites = siteDAO.requestMostRecentSites();
			
			return sites;
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}

	public ArrayList<Site> requestSites(String[] regions, boolean sectorsNumbersEnabled, int sectorsNumbers, boolean ratingEnabled,
			String rating, boolean official, String keywordsString) throws ServiceException {
		String[] keywords = (keywordsString.length() == 0) ? null : keywordsString.split(" ");
		
		try {
			ArrayList<Site> sites = siteDAO.requestSites(regions, sectorsNumbersEnabled, sectorsNumbers, ratingEnabled, rating, official, keywords);
			
			for (Site site : sites) {
				List<Sector> sectors = sectorDAO.requestSectorsBySite(site.getId());
			
				site.setSectors(sectors);
			}
			
			return sites;
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public ArrayList<String> requestRegions() throws ServiceException {
		try {
			return siteDAO.requestRegions();
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public int getMinSectorsCount() throws ServiceException {
		try {
			return sectorDAO.getMinSectorCount();
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
	
	public int getMaxSectorsCount() throws ServiceException {
		try {
			return sectorDAO.getMaxSectorCount();
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
