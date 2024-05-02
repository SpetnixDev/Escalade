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
import com.escalade.services.ReservationService;
import com.escalade.services.TopoService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ReservationServlet
 */
@WebServlet("/reservetopo")
public class ReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TopoService topoService;
	private ReservationService reservationService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationServlet() {
        super();
        
        this.topoService = new TopoService();
        this.reservationService = new ReservationService();
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
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if (topoService.isTopoAvailable(topoId) && user != null) {
			reservationService.createReservation(user.getId(), topoId);
		} else {
			session.setAttribute("errorOccured", "Une erreur est survenue, veuillez réessayez plus tard.");
			response.getWriter().write("/Escalade/home");
		}
	}
}
