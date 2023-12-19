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
        userService = new UserService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		boolean authenticationPassed = verifyAuthenticationInformations(username, password);
		
		if (authenticationPassed) {
			HttpSession session = request.getSession();
			
			User user = userService.createUser(username);
			session.setAttribute("user", user);
			
			response.sendRedirect("/Escalade/home");
		} else {
			request.setAttribute("incorrectLogin", "Identifiants incorrects");
			
			request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
		}
	}

	private boolean verifyAuthenticationInformations(String username, String password) {
		if (password.equals("abc123")) {
			return true;
		}
		
		return false;
	}

}
