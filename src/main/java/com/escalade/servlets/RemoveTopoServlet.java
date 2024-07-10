package com.escalade.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.escalade.services.ServiceException;
import com.escalade.services.topo.RemoveTopoService;
import com.escalade.util.ConnectionChecker;
import com.escalade.util.ConnectionException;
import com.escalade.util.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class RemoveTopoServlet
 */
@WebServlet("/removetopo")
public class RemoveTopoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RemoveTopoService removeTopoService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveTopoServlet() {
        super();
        removeTopoService = new RemoveTopoService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		int topoId = Integer.parseInt((String) mapper.readValue(request.getInputStream(), Map.class).get("topoId"));
		
		try {
			ConnectionChecker.getSessionUser(request);
			
			if (!removeTopoService.removeTopo(topoId)) {
				request.getSession().setAttribute("error", "Une erreur est survenue, veuillez réessayez plus tard.");
				response.sendRedirect("/Escalade/home");
			}
		} catch(ConnectionException | ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
	}

}
