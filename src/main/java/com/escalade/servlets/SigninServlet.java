package com.escalade.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.escalade.model.User;
import com.escalade.services.ServiceException;
import com.escalade.services.user.UserAuthenticationService;
import com.escalade.util.HttpUtils;

/**
 * Servlet implementation class SigninServlet
 */
@WebServlet("/signin")
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	
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
		HttpSession session = request.getSession();
		String errorMessage = (String) session.getAttribute("error");
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
		
		if (errorMessage != null) {
			session.removeAttribute("error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user;
		
		try {
			if ((user = userAuthenticationService.authenticateUser(email, password)) != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				
				response.sendRedirect("/Escalade/home");
			} else {
				request.setAttribute("incorrectLogin", "Identifiants incorrects");
				
				request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
			}
		} catch (ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
	}
}
