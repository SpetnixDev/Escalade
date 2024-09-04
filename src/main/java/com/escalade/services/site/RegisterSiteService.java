package com.escalade.services.site;

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
public class RegisterSiteService {
	@Autowired
	private SiteDAO siteDAO;
	@Autowired
	private SectorDAO sectorDAO;
	@Autowired
	private RouteDAO routeDAO;
	@Autowired
	private PitchDAO pitchDAO;
	
	public int registerSite(Site site) throws ServiceException {
		try {
			int siteId = siteDAO.registerSite(site.getName(), site.getDescription(), site.getRegion(), site.getRating());
			
			for (Sector sector : site.getSectors()) {
				int sectorId = sectorDAO.registerSector(siteId, sector.getName(), sector.getDescription());
				
				for (Route route : sector.getRoutes()) {
					int routeId = routeDAO.registerRoute(sectorId, route.getName(), route.getDescription());
					
					for (Pitch pitch : route.getPitches()) {
						pitchDAO.registerPitch(routeId, pitch.getName(), pitch.getDescription(), pitch.getLength(), pitch.getDifficulty());
					}
				}
			}
			
			return siteId;
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
