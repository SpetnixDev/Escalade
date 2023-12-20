package com.escalade.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.escalade.model.Topo;
import com.escalade.services.TopoService;

/**
 * Servlet implementation class ToposServlet
 */
@WebServlet("/topos")
public class ToposServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TopoService toposService;
	private List<String> regionsList;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToposServlet() {
        super();
        toposService = new TopoService();
        regionsList = toposService.requestRegions();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("regionsList", regionsList);
		
		ArrayList<Topo> researchResults = toposService.requestTopos(null, null);

		request.setAttribute("results", researchResults);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/topos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] regions = request.getParameterValues("region");
		String[] keywords = request.getParameter("searchbar").strip().split(" ");
				
		ArrayList<Topo> researchResults = toposService.requestTopos(regions, keywords);
				
		request.setAttribute("regionsList", regionsList);
		request.setAttribute("results", researchResults);
				
		request.getRequestDispatcher("/WEB-INF/views/topos.jsp").forward(request, response);
	}
}
