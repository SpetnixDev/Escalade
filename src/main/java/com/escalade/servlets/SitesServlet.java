package com.escalade.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	private RequestSiteService requestSiteService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SitesServlet() {
        super();
        
        requestSiteService = new RequestSiteService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Site> researchResults = null;
		
		try {
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
		boolean official = request.getParameter("official") != null;
		
		ArrayList<Site> researchResults = null;
		try {
			researchResults = requestSiteService.requestSites();
		} catch (ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
		
		request.setAttribute("results", researchResults);
		
		request.getRequestDispatcher("/WEB-INF/views/sites.jsp").forward(request, response);
	}

}
