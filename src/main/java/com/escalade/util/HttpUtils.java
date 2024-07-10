package com.escalade.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtils {
	public static String getPathVariable(String pathInfo) {
		if (pathInfo == null || pathInfo.length() < 2) return "";
		
		String[] pathParts = pathInfo.split("/");
		
		return pathParts[1];
	}
	
	public static void handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
		request.getSession().setAttribute("error", e.getMessage());
		
		if (e instanceof ConnectionException) {
			response.sendRedirect("/Escalade/signin");
		} else {
			response.sendRedirect("/Escalade/home");
		}
	}
	
	public static String getExceptionRedirectionPath(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
		request.getSession().setAttribute("error", e.getMessage());
		
		if (e instanceof ConnectionException) {
			return "/Escalade/signin";
		} else {
			return "/Escalade/home";
		}
	}
}
