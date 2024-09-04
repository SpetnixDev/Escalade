package com.escalade.servlets;

import java.io.IOException;
import java.util.ArrayList;

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

import com.escalade.model.Topo;
import com.escalade.services.ServiceException;
import com.escalade.services.topo.RequestTopoService;
import com.escalade.util.HttpUtils;

/**
 * Servlet implementation class ToposServlet
 */
@WebServlet("/topos")
public class ToposServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RequestTopoService requestTopoService;
	private ArrayList<Topo> researchResults;
	
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
		request.setCharacterEncoding("UTF-8");
		
		ArrayList<String> regionsList;
		
		try {
			regionsList = requestTopoService.requestLocations();
			researchResults = requestTopoService.requestTopos();
		} catch (ServiceException e) {
			HttpUtils.handleException(request, response, e);
			
			return;
		}
		
		request.setAttribute("regionsList", regionsList);
		request.setAttribute("results", researchResults);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/topos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		ArrayList<String> regionsList;
		
		String[] regions = request.getParameterValues("region");
		String keywordsString = request.getParameter("searchbar").strip();		

		
		try {
			regionsList = requestTopoService.requestLocations();
			researchResults = requestTopoService.requestTopos(regions, keywordsString);
		} catch (ServiceException e) {
			HttpUtils.handleException(request, response, e);
			
			return;
		}

		request.setAttribute("regionsList", regionsList);
		request.setAttribute("results", researchResults);
				
		request.getRequestDispatcher("/WEB-INF/views/topos.jsp").forward(request, response);
	}
}
