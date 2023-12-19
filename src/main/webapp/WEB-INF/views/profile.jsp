<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Mon Profil</title>
	<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<%@ include file="../shared/header.jsp" %>

	<h1>Mon Profil</h1>
	<p>Nom d'utilisateur : ${sessionScope.user.username}</p>
	
	<c:choose>
		<c:when test="${not empty sessionScope.user.topos}">
			<h2>Vos topos :</h2>
			
			<c:forEach items="${ sessionScope.user.topos }" var="topo">
    				 <p><c:out value="${ topo.title }" /></p>
				</c:forEach>
		</c:when>
									
		<c:otherwise>
			<p style="color: red;">Vous n'avez pas de topos enregistrés.</p>
		</c:otherwise>
	</c:choose>
</body>
</html>