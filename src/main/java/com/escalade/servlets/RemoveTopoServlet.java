package com.escalade.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.model.User;
import com.escalade.services.TopoService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class RemoveTopoServlet
 */
@WebServlet("/removetopo")
public class RemoveTopoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TopoService topoService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveTopoServlet() {
        super();
        this.topoService = new TopoService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		int topoId = Integer.parseInt((String) mapper.readValue(request.getInputStream(), Map.class).get("topoId"));
	    
	    HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			response.sendRedirect("/Escalade/signin");
		} else {		    
		    if (!topoService.removeTopo(topoId)) {
		    	session.setAttribute("errorOccured", "Une erreur est survenue, veuillez réessayez plus tard.");
				response.getWriter().write("/Escalade/home");
		    }
		}
	}

}
