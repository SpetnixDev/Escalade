package com.escalade.servlets;

import java.io.IOException;
import java.util.Map;

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

import com.escalade.services.ServiceException;
import com.escalade.services.site.UpdateSiteService;
import com.escalade.util.ConnectionChecker;
import com.escalade.util.ConnectionException;
import com.escalade.util.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class UpdateOfficialSiteServlet
 */
@WebServlet("/updateofficialsite")
public class UpdateOfficialSiteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UpdateSiteService updateSiteService;
	
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		int siteId = Integer.parseInt((String) mapper.readValue(request.getInputStream(), Map.class).get("siteId"));
		
		try {
			ConnectionChecker.getSessionUser(request);
			
			if (updateSiteService.updateOfficialStatus(siteId)) {
				response.sendRedirect("/Escalade/site/" + siteId);
			}
		} catch (ConnectionException | ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
	}

}
