package com.escalade.services.site;

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

public class RegisterSiteService {
	private SiteDAOImpl siteDAOImpl;
	private SectorDAOImpl sectorDAOImpl;
	private RouteDAOImpl routeDAOImpl;
	private PitchDAOImpl pitchDAOImpl;
	
	public RegisterSiteService() {
		DAOFactory daoFactory = DAOFactory.getInstance();
		
		siteDAOImpl = (SiteDAOImpl) daoFactory.getSiteDAO();
		sectorDAOImpl = (SectorDAOImpl) daoFactory.getSectorDAO();
		routeDAOImpl = (RouteDAOImpl) daoFactory.getRouteDAO();
		pitchDAOImpl = (PitchDAOImpl) daoFactory.getPitchDAO();
	}
	
	public int registerSite(Site site) throws ServiceException {
		try {
			int siteId = siteDAOImpl.registerSite(site.getName(), site.getDescription(), site.getRegion());
			
			System.out.println(site.getSectors().size());
			
			for (Sector sector : site.getSectors()) {
				int sectorId = sectorDAOImpl.registerSector(siteId, sector.getName(), sector.getDescription());
				
				for (Route route : sector.getRoutes()) {
					int routeId = routeDAOImpl.registerRoute(sectorId, route.getName(), route.getDescription());
					
					for (Pitch pitch : route.getPitches()) {
						pitchDAOImpl.registerPitch(routeId, pitch.getName(), pitch.getDescription(), pitch.getLength(), pitch.getDifficulty());
					}
				}
			}
			
			return siteId;
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
