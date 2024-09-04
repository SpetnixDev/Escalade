package com.escalade.servlets;

import java.io.IOException;

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
	
	@Autowired
	private RegisterUserService registerUserService;
	
	@Autowired
	private RequestUserService requestUserService;
	
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
