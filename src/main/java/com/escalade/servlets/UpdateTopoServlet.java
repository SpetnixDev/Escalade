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

import com.escalade.model.Topo;
import com.escalade.model.User;
import com.escalade.services.ServiceException;
import com.escalade.services.topo.RequestTopoService;
import com.escalade.services.topo.UpdateTopoService;
import com.escalade.util.ConnectionChecker;
import com.escalade.util.ConnectionException;
import com.escalade.util.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/updatetopo")
public class UpdateTopoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UpdateTopoService updateTopoService;
	
	@Autowired
	private RequestTopoService requestTopoService;
	
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
		
		Map<String, Object> requestBody = mapper.readValue(request.getInputStream(), new TypeReference<Map<String, Object>>(){});
	    
		try {
			User user = ConnectionChecker.getSessionUser(request);
			

		    String topoIdString = (String) requestBody.get("topoId");
		    int topoId = Integer.parseInt(topoIdString);
		    
		    Boolean topoAvailable = (Boolean) requestBody.get("topoAvailable");
		    
		    Topo topo = null;
		    
		    if ((topo = updateTopoService.updateTopoAvailability(topoId, !topoAvailable)) != null) {
			    String jsonResponse = mapper.writeValueAsString(topo);
			    
			    user.setTopos(requestTopoService.requestToposByUser(user.getId()));
			    
			    response.setContentType("application/json");
			    response.getWriter().write(jsonResponse);
		    }
		} catch (ConnectionException | ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
	}
}
