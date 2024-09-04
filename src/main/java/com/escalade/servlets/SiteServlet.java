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

import com.escalade.model.Comment;
import com.escalade.model.Site;
import com.escalade.services.ServiceException;
import com.escalade.services.comment.RequestCommentsService;
import com.escalade.services.site.RequestSiteService;
import com.escalade.util.HttpUtils;

/**
 * Servlet implementation class Site
 */
@WebServlet("/site/*")
public class SiteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RequestCommentsService requestCommentsService;
	
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
		// TODO Auto-generated method stub
		String id = HttpUtils.getPathVariable(request.getPathInfo());
		
		Site site = null;
		ArrayList<Comment> comments = null;
		
		try {
			int siteId = Integer.valueOf(id);
			
			site = requestSiteService.requestSite(siteId);
			comments = requestCommentsService.requestCommentsBySite(siteId);
		} catch (NumberFormatException e) {
			site = null;
		} catch (ServiceException e) {
			HttpUtils.handleException(request, response, e);
		}
		
		if (site == null) {
			response.sendRedirect("/Escalade/site-error");
			
			return;
		} else {
			request.setAttribute("site", site);
			request.setAttribute("comments", comments);
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/site.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
