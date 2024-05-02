<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Les Amis de l'Escalade</title>
		<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp" %>
		
		<c:if test="${not empty sessionScope.errorOccured}">
	        <div id="errorDiv" class="error-message">
	            <span style="color: red;">${sessionScope.errorOccured}</span>
	        </div>
    	</c:if>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
	</body>
</html>