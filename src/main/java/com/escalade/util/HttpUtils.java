package com.escalade.util;

public class HttpUtils {
	public static String getPathVariable(String pathInfo) {
		if (pathInfo == null || pathInfo.length() < 2) return "";
		
		String[] pathParts = pathInfo.split("/");
		
		return pathParts[1];
	}
}
