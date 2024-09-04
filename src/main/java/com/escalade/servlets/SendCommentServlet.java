package com.escalade.servlets;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.escalade.model.Comment;
import com.escalade.model.User;
import com.escalade.services.ServiceException;
import com.escalade.services.comment.SendCommentService;
import com.escalade.util.ConnectionChecker;
import com.escalade.util.ConnectionException;
import com.escalade.util.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class SendCommentServlet
 */
@WebServlet("/sendcomment")
public class SendCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SendCommentService sendCommentService;
	
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			Map<String, String> data = mapper.readValue(request.getInputStream(), new TypeReference<Map<String, String>>(){});

            String comment = data.get("comment");
            String siteId = data.get("siteId");
            
            Date date = Date.valueOf(LocalDate.now());
			
			User user = ConnectionChecker.getSessionUser(request);

			ArrayList<Comment> comments = sendCommentService.sendComment(user.getId(), Integer.parseInt(siteId), comment, date);
			
			Map<String, Object> responseMap = new HashMap<>();
	        responseMap.put("comments", comments);
	        responseMap.put("isMember", user.isMember());

	        mapper.writeValue(response.getWriter(), responseMap);
		} catch(ConnectionException | ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
	}

}
