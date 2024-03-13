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
import com.escalade.utils.PasswordHashing;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
        this.userService = new UserService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");
		
		if (userService.isEmailAlreadyUsed(email)) {
			request.setAttribute("emailAlreadyUsed", "L'email entré est déjà utilisé");
			
			request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
		} else {
			password = PasswordHashing.hashPassword(password);
			
			HttpSession session = request.getSession();
			
			User user = userService.registerUser(firstName, lastName, email, password);
			System.out.println(user);
			session.setAttribute("user", user);
			
			response.sendRedirect("/Escalade/home");
		}
	}
}
