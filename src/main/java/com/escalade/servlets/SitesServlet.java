package com.escalade.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.escalade.model.Site;
import com.escalade.services.ServiceException;
import com.escalade.services.site.RequestSiteService;
import com.escalade.util.HttpUtils;

/**
 * Servlet implementation class SitesServlet
 */
@WebServlet("/sites")
public class SitesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RequestSiteService requestSiteService;
	
	private WebApplicationContext springContext;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		ArrayList<Site> researchResults = null;
		
		try {
			request.setAttribute("regions", requestSiteService.requestRegions());
			
			int minSectors = requestSiteService.getMinSectorsCount();
			
			request.setAttribute("minSectors", minSectors);
			request.setAttribute("sectors", minSectors);
			request.setAttribute("maxSectors", requestSiteService.getMaxSectorsCount());
			
			researchResults = requestSiteService.requestSites();
		} catch (ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
		
		request.setAttribute("results", researchResults);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/sites.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String[] regions = request.getParameterValues("region");
		boolean sectorsNumbersEnabled = request.getParameter("sectorsNumbersEnabled") != null;
		int sectors = Integer.parseInt(request.getParameter("sectors"));
		boolean official = request.getParameter("official") != null;
		String keywordsString = request.getParameter("searchbar").strip();
		boolean ratingEnabled = request.getParameter("ratingEnabled") != null;
		String rating = request.getParameter("rating");
		
		ArrayList<Site> researchResults = null;
		
		try {
			request.setAttribute("regions", requestSiteService.requestRegions());
			
			int minSectors = requestSiteService.getMinSectorsCount();
			
			request.setAttribute("minSectors", minSectors);
			request.setAttribute("sectors", minSectors);
			request.setAttribute("maxSectors", requestSiteService.getMaxSectorsCount());
			
			researchResults = requestSiteService.requestSites(regions, sectorsNumbersEnabled, sectors, ratingEnabled, 
					rating, official, keywordsString);
		} catch (ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
		
		request.setAttribute("results", researchResults);
		
		request.getRequestDispatcher("/WEB-INF/views/sites.jsp").forward(request, response);
	}

}
