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
	private TopoService topoService;
	private List<String> regionsList;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToposServlet() {
        super();
        topoService = new TopoService();
        regionsList = topoService.requestLocations();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		request.setAttribute("regionsList", regionsList);
		
		List<Topo> researchResults = topoService.requestTopos();

		request.setAttribute("results", researchResults);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/topos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String[] regions = request.getParameterValues("region");
		String[] keywords = request.getParameter("searchbar").strip().split(" ");
				
		ArrayList<Topo> researchResults = (ArrayList<Topo>) topoService.requestTopos(regions, keywords);
				
		request.setAttribute("regionsList", regionsList);
		request.setAttribute("results", researchResults);
				
		request.getRequestDispatcher("/WEB-INF/views/topos.jsp").forward(request, response);
	}
}
