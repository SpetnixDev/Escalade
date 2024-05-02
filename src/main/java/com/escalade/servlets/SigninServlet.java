package com.escalade.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.escalade.model.User;
import com.escalade.services.UserService;
import com.escalade.util.PasswordHashing;

/**
 * Servlet implementation class SigninServlet
 */
@WebServlet("/signin")
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SigninServlet() {
        super();
    	this.userService = new UserService();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String hashedPassword = PasswordHashing.hashPassword(request.getParameter("password"));
		User user;
		
		if ((user = userService.authenticateUser(email, hashedPassword)) != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			response.sendRedirect("/Escalade/home");
		} else {
			request.setAttribute("incorrectLogin", "Identifiants incorrects");
			
			request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
		}
	}
}
