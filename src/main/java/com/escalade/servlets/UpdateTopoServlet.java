package com.escalade.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.escalade.model.Topo;
import com.escalade.services.TopoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/updatetopo")
public class UpdateTopoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TopoService topoService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTopoServlet() {
        super();
        this.topoService = new TopoService();
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
		
		Map<String, Object> requestBody = mapper.readValue(request.getInputStream(), new TypeReference<Map<String,Object>>(){});
	    
	    String topoIdString = (String) requestBody.get("topoId");
	    int topoId = Integer.parseInt(topoIdString);
	    
	    String topoAvailableString = (String) requestBody.get("topoAvailable");
	    boolean topoAvailable = Boolean.parseBoolean(topoAvailableString);
	    
	    Topo topo = null;
	    
	    if ((topo = topoService.updateTopoAvailability(topoId, topoAvailable)) != null) {
		    String jsonResponse = mapper.writeValueAsString(topo);
		    
		    response.setContentType("application/json");
		    response.getWriter().write(jsonResponse);
	    }
	}
}
