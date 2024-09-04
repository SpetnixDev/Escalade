package com.escalade.servlets;

import java.io.BufferedReader;
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

import com.escalade.model.Site;
import com.escalade.services.ServiceException;
import com.escalade.services.site.RegisterSiteService;
import com.escalade.util.ConnectionChecker;
import com.escalade.util.ConnectionException;
import com.escalade.util.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class RegisterSiteServlet
 */
@WebServlet("/registersite")
public class RegisterSiteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RegisterSiteService registerSiteService;
	
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
		try {
			ConnectionChecker.getSessionUser(request);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/registersite.jsp").forward(request, response);
		} catch (ConnectionException e) {
			HttpUtils.handleException(request, response, e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String redirect = "";
        
        try {
        	ConnectionChecker.getSessionUser(request);
    		
    		BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            String json = jsonBuilder.toString();
            
            ObjectMapper objMapper = new ObjectMapper();
            Site site = objMapper.readValue(json, Site.class);
            
        	int siteId = registerSiteService.registerSite(site);
        	
        	redirect = "/Escalade/site/" + siteId;
        } catch (ConnectionException | ServiceException e) {
        	redirect = HttpUtils.getExceptionRedirectionPath(request, response, e);
        }
    	
    	response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(redirect);
	}

}
