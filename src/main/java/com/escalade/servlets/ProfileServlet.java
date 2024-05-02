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
import com.escalade.services.ReservationService;
import com.escalade.services.ServiceException;
import com.escalade.services.TopoService;
import com.escalade.services.topo.RequestTopoService;
import com.escalade.util.ConnectionChecker;
import com.escalade.util.ConnectionException;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ReservationService reservationService;
    private RequestTopoService requestTopoService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        
        this.reservationService = new ReservationService();
        this.requestTopoService = new RequestTopoService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*User user = (User) request.getSession().getAttribute("user");
		
		if (user != null) {
			user.setTopos(topoService.requestToposFromUser(user.getId()));
			List<Reservation> reservations = reservationService.getReservationsConcerningUser(user.getId());
			request.setAttribute("reservations", reservations);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
		} else {
			response.sendRedirect("/Escalade/signin");
		}*/
		
		try {
			User user = ConnectionChecker.getSessionUser(request);
			
			user.setTopos(requestTopoService.requestToposByUser(user.getId()));
			
			List<Reservation> reservations = reservationService.getReservationsConcerningUser(user.getId());
			request.setAttribute("reservations", reservations);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
		} catch (ConnectionException | ServiceException e) {
			request.getSession().setAttribute("error", e.getMessage());
			
			response.sendRedirect("/Escalade/home");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
