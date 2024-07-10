package com.escalade.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.escalade.model.User;
import com.escalade.services.ServiceException;
import com.escalade.services.reservation.CreateReservationService;
import com.escalade.services.topo.RequestTopoService;
import com.escalade.util.ConnectionChecker;
import com.escalade.util.ConnectionException;
import com.escalade.util.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ReservationServlet
 */
@WebServlet("/reservetopo")
public class ReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestTopoService requestTopoService;
	private CreateReservationService createReservationService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationServlet() {
        super();
        
        requestTopoService = new RequestTopoService();
        createReservationService = new CreateReservationService();
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
			User user = ConnectionChecker.getSessionUser(request);
			
			if (requestTopoService.isTopoAvailable(topoId)) {
				createReservationService.createReservation(user.getId(), topoId);
				response.sendRedirect("/Escalade/topos");
			} else {
				request.getSession().setAttribute("error", "Une erreur est survenue, veuillez réessayez plus tard.");
				response.sendRedirect("/Escalade/home");
			}
		} catch (ConnectionException | ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
	}
}
