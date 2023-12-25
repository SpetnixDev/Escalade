package com.escalade.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.escalade.model.Site;
import com.escalade.services.SiteService;

/**
 * Servlet implementation class SitesServlet
 */
@WebServlet("/sites")
public class SitesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SiteService siteService;
	private List<String> regionsList;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SitesServlet() {
        super();
        siteService = new SiteService();
        regionsList = siteService.requestRegions();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("regionsList", regionsList);
		
		ArrayList<Site> researchResults = siteService.requestSites(null, null, false);
		
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
		
		ArrayList<Site> researchResults = siteService.requestSites(regions, null, official);
		
		request.setAttribute("regionsList", regionsList);
		request.setAttribute("results", researchResults);
		
		request.getRequestDispatcher("/WEB-INF/views/sites.jsp").forward(request, response);
	}

}
