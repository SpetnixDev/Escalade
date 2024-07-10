package com.escalade.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.escalade.model.User;
import com.escalade.services.ServiceException;
import com.escalade.services.topo.RegisterTopoService;
import com.escalade.services.topo.RequestTopoService;
import com.escalade.util.ConnectionChecker;
import com.escalade.util.ConnectionException;
import com.escalade.util.HttpUtils;
import com.escalade.util.StringUtils;

/**
 * Servlet implementation class NewTopoServlet
 */
@WebServlet("/registertopo")
public class NewTopoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RegisterTopoService registerTopoService;
	private RequestTopoService requestTopoService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewTopoServlet() {
        super();

        registerTopoService = new RegisterTopoService();
        requestTopoService = new RequestTopoService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ConnectionChecker.getSessionUser(request);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/registertopo.jsp").forward(request, response);
		} catch (ConnectionException e) {
			HttpUtils.handleException(request, response, e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String location = request.getParameter("location");
		Date releaseDate = StringUtils.parseDate(request.getParameter("releaseDate"));
		
		try {
			User user = ConnectionChecker.getSessionUser(request);
			
			if (registerTopoService.registerNewTopo(user.getId(), title, description, location, releaseDate)) {
				user.setTopos(requestTopoService.requestToposByUser(user.getId()));
				response.sendRedirect("/Escalade/profile");
			} else {
				request.setAttribute("registerFailed", "Erreur lors de l'ajout d'un nouveau topo");
				request.getRequestDispatcher("/WEB-INF/views/registertopo.jsp").forward(request, response);
			}
		} catch (ConnectionException | ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
	}

}
