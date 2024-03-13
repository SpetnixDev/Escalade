package com.escalade.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.escalade.model.Site;
import com.escalade.services.SiteService;
import com.escalade.utils.HttpUtils;

/**
 * Servlet implementation class Site
 */
@WebServlet("/site/*")
public class SiteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SiteService siteService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SiteServlet() {
        super();
        
        siteService = new SiteService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = HttpUtils.getPathVariable(request.getPathInfo());
		Site site;
		
		try {
			int siteId = Integer.valueOf(id);
			
			site = siteService.getSiteByID(siteId);
		} catch (NumberFormatException e) {
			site = null;
		}
		
		if (site == null) {
			response.sendRedirect("/Escalade/site-error");
			
			return;
		} else {
			request.setAttribute("site", site);
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/site.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
