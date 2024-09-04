package com.escalade.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.escalade.model.DetailedReservation;
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
	
	@Autowired
	private RequestReservationService requestReservationService;
    
	@Autowired
	private RequestTopoService requestTopoService;
	
	private WebApplicationContext springContext;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = ConnectionChecker.getSessionUser(request);
			
			user.setTopos(requestTopoService.requestToposByUser(user.getId()));
			
			List<Reservation> reservations = requestReservationService.requestReservationsConcerningUser(user.getId());
			List<DetailedReservation> userReservations = requestReservationService.requestReservationsByUser(user.getId());

			request.setAttribute("reservations", reservations);
			request.setAttribute("userReservations", userReservations);
			
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
