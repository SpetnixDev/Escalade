package com.escalade.services.site;

import java.util.ArrayList;
import java.util.List;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.dao.pitch.PitchDAOImpl;
import com.escalade.dao.route.RouteDAOImpl;
import com.escalade.dao.sector.SectorDAOImpl;
import com.escalade.dao.site.SiteDAOImpl;
import com.escalade.model.Pitch;
import com.escalade.model.Route;
import com.escalade.model.Sector;
import com.escalade.model.Site;
import com.escalade.services.ServiceException;

public class RequestSiteService {
	private SiteDAOImpl siteDAOImpl;
	private SectorDAOImpl sectorDAOImpl;
	private RouteDAOImpl routeDAOImpl;
	private PitchDAOImpl pitchDAOImpl;
	
	public RequestSiteService() {
		DAOFactory daoFactory = DAOFactory.getInstance();
		
		siteDAOImpl = (SiteDAOImpl) daoFactory.getSiteDAO();
		sectorDAOImpl = (SectorDAOImpl) daoFactory.getSectorDAO();
		routeDAOImpl = (RouteDAOImpl) daoFactory.getRouteDAO();
		pitchDAOImpl = (PitchDAOImpl) daoFactory.getPitchDAO();
	}
	
	public Site requestSite(int siteId) throws ServiceException {
		try {
			Site site = siteDAOImpl.requestSiteById(siteId);
			
			List<Sector> sectors = sectorDAOImpl.requestSectorsBySite(siteId);
			
			for (Sector sector : sectors) {
				List<Route> routes = routeDAOImpl.requestRoutesBySector(sector.getId());
				
				for (Route route : routes) {
					List<Pitch> pitches = pitchDAOImpl.requestPitchesByRoute(route.getId());
					
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
			ArrayList<Site> sites = siteDAOImpl.requestSites();
			
			for (Site site : sites) {
				List<Sector> sectors = sectorDAOImpl.requestSectorsBySite(site.getId());
			
				site.setSectors(sectors);
			}
			
			return sites;
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
