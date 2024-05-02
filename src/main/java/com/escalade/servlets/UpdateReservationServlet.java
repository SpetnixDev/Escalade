package com.escalade.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.model.Reservation;
import com.escalade.model.User;
import com.escalade.services.ReservationService;
import com.escalade.services.TopoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class UpdateReservationServlet
 */
@WebServlet("/updatereservation")
public class UpdateReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TopoService topoService;
	private ReservationService reservationService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateReservationServlet() {
        super();

        this.topoService = new TopoService();
        this.reservationService = new ReservationService();
        // TODO Auto-generated constructor stub
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
		
		Map<String, Object> requestBody = mapper.readValue(request.getInputStream(), new TypeReference<Map<String, Object>>(){});
	    
	    HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			response.sendRedirect("/Escalade/signin");
		} else {
		    String resIdString = (String) requestBody.get("resId");
		    int resId = Integer.parseInt(resIdString);
		    
		    String reservationStatus = (String) requestBody.get("reservationStatus");
		    
		    Reservation reservation = null;
		    
		    if ((reservation = reservationService.updateReservation(resId, reservationStatus)) != null) {
			    String jsonResponse = mapper.writeValueAsString(reservation);
			    
			    user.setTopos(topoService.requestToposFromUser(user.getId()));
			    
			    response.setContentType("application/json");
			    response.getWriter().write(jsonResponse);
		    }
		}
	}

}
