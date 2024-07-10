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
		
		<h4 class="site-desc">${site.description}</h4>
		
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
								<td>${site.getLength()}</td>
							</tr>
							<tr>
								<td><div>Secteurs</div></td>
								<td>${site.sectors.size()}</td>
							</tr>
							<tr>
								<td><div>Voies</div></td>
								<td>${site.getNumberOfRoutes()}</td>
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
							
							<button class="button" onclick="toggleContent(this, event)">+</button>
						</div>
						
						<div class="collapsible-content">
							<p>${sector.description}</p>
							<p>Ce secteur contient <b>${sector.routes.size()} voie(s)</b> pour un total de <b>${sector.getFormattedLength()}</b>.</p>
							
							<c:forEach var="route" items="${sector.routes}">
								<div class="box collapsible">
									<div class="route">
										<h3>${route.name}</h3>
										
										<button class="button" onclick="toggleContent(this, event)">+</button>
									</div>
									
									<div class="collapsible-content">
										<p>${route.description}</p>
										<p>Cette voie contient <b>${route.pitches.size()} longueur(s)</b> pour un total de <b>${route.getFormattedLength()}</b>.</p>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
	</body>
</html>