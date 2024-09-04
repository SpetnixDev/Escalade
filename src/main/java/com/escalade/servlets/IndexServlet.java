package com.escalade.servlets;

import java.io.IOException;
import java.util.ArrayList;

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

import com.escalade.model.Site;
import com.escalade.services.ServiceException;
import com.escalade.services.site.RequestSiteService;
import com.escalade.util.HttpUtils;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/home")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RequestSiteService requestSiteService;
	
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
		
		try {
			ArrayList<Site> sites = requestSiteService.requestMostRecentSites();
			request.setAttribute("sites", sites);
		} catch (ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
		
		if (errorMessage != null) {
			session.removeAttribute("error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
