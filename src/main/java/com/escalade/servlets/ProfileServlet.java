package com.escalade.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.escalade.model.Reservation;
import com.escalade.model.User;
import com.escalade.services.ServiceException;
import com.escalade.services.reservation.RequestReservationService;
import com.escalade.services.topo.RequestTopoService;
import com.escalade.util.ConnectionChecker;
import com.escalade.util.ConnectionException;
import com.escalade.util.HttpUtils;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestReservationService requestReservationService;
    private RequestTopoService requestTopoService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        
        this.requestReservationService = new RequestReservationService();
        this.requestTopoService = new RequestTopoService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = ConnectionChecker.getSessionUser(request);
			
			user.setTopos(requestTopoService.requestToposByUser(user.getId()));
			
			List<Reservation> reservations = requestReservationService.requestReservationsConcerningUser(user.getId());
			request.setAttribute("reservations", reservations);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
		} catch (ConnectionException | ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
