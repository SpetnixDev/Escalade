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
	
	<h1 class="page-title">Mon Profil</h1>

	<div class="container">
		<div class="box-list box-list-left">
			<div class="box site-infos">
				<table class="infos-table">
					<thead>
						<tr>
							<th colspan="2" class="site-infos-title">Informations</th>
						</tr>
					</thead>
					
					<tbody>
						<tr>
							<td><div>Nom</div></td>
							<td>${sessionScope.user.lastName}</td>
						</tr>
						<tr>
							<td><div>Prénom</div></td>
							<td>${sessionScope.user.firstName}</td>
						</tr>
						<tr>
							<td><div>Email</div></td>
							<td>${sessionScope.user.email}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="box-list box-list-right">
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
		</div>
	</div>
</body>
</html>