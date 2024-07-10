package com.escalade.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.escalade.model.Topo;
import com.escalade.services.ServiceException;
import com.escalade.services.topo.RequestTopoService;
import com.escalade.util.HttpUtils;

/**
 * Servlet implementation class ToposServlet
 */
@WebServlet("/topos")
public class ToposServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RequestTopoService requestTopoService;
	private ArrayList<Topo> researchResults;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToposServlet() {
        super();
        
        requestTopoService = new RequestTopoService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		try {
			request.setAttribute("regionsList", requestTopoService.requestLocations());
			researchResults = requestTopoService.requestTopos();
		} catch (ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}

		request.setAttribute("results", researchResults);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/topos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String[] regions = request.getParameterValues("region");
		String keywordsString = request.getParameter("searchbar").strip();		

		
		try {
			request.setAttribute("regionsList", requestTopoService.requestLocations());
			researchResults = requestTopoService.requestTopos(regions, keywordsString);
		} catch (ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
		
		request.setAttribute("results", researchResults);
				
		request.getRequestDispatcher("/WEB-INF/views/topos.jsp").forward(request, response);
	}
}
