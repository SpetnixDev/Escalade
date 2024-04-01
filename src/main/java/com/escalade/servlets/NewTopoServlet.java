package com.escalade.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.escalade.model.User;
import com.escalade.services.TopoService;
import com.escalade.utils.StringUtils;

/**
 * Servlet implementation class NewTopoServlet
 */
@WebServlet("/registertopo")
public class NewTopoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TopoService topoService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewTopoServlet() {
        super();
        this.topoService = new TopoService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/registertopo.jsp").forward(request, response);
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
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			response.sendRedirect("/Escalade/signin");
		} else {
			if (topoService.registerNewTopo(user.getId(), title, description, location, releaseDate)) {
				user.setTopos(topoService.requestToposFromUser(user.getId()));
				
				response.sendRedirect("/Escalade/profile");
			} else {
				request.setAttribute("registerFailed", "Erreur lors de l'ajout d'un nouveau topo");
				
				request.getRequestDispatcher("/WEB-INF/views/registertopo.jsp").forward(request, response);
			}
		}
	}

}
