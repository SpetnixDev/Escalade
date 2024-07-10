package com.escalade.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.escalade.model.User;
import com.escalade.services.ServiceException;
import com.escalade.services.user.RegisterUserService;
import com.escalade.services.user.RequestUserService;
import com.escalade.util.HttpUtils;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RegisterUserService registerUserService;
	private RequestUserService requestUserService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();

        registerUserService = new RegisterUserService();
        requestUserService = new RequestUserService();
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
		
		try {
			if (requestUserService.isEmailAlreadyUsed(email)) {
				request.setAttribute("emailAlreadyUsed", "L'email entré est déjà utilisé");
				
				request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
			} else {
				User user = registerUserService.registerUser(email, firstName, lastName, password);
				
				request.getSession().setAttribute("user", user);
				
				response.sendRedirect("/Escalade/home");
			}
		} catch (ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
	}
}
