<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>${site.name}</title>
		<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp" %>
	
		<h1 class="page-title">${site.name}</h1>
		
		<div class="container">
			<div class="box-list box-list-left">
				<div class="box site-infos">
					<table class="infos-table">
						<thead>
							<tr>
								<th colspan="2" class="infos-title">Informations</th>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td><div>Région</div></td>
								<td>${site.region}</td>
							</tr>
							<tr>
								<td><div>Longueur</div></td>
								<td>6.24 Km</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="box-list box-list-right">
				<c:forEach var="sector" items="${site.sectors}">
					<div class="box collapsible">
						<div class="sector">
							<h3>${sector.name}</h3>
							
							<button class="button" onclick="toggleContent(this)">+</button>
						</div>
						
						<div class="collapsible-content">
							<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
	</body>
</html>