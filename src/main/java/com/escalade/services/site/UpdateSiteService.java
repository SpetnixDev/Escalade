package com.escalade.services.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.site.SiteDAO;
import com.escalade.services.ServiceException;

@Service
public class UpdateSiteService {
	@Autowired
	private SiteDAO siteDAO;
	
	public boolean updateOfficialStatus(int siteId) throws ServiceException {
		try {
			return siteDAO.updateOfficialStatus(siteId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
