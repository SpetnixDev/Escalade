<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Recherche de sites</title>
		<link href="${pageContext.request.contextPath}/resources/style.css"
			rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp"%>
	
		<h1 class="page-title">Sites d'escalade</h1>
	
		<div class="container">
			<div class="box-list box-list-left">
				<form method="post">
					<h2 class="research-title">Recherche</h2>
	
					<div class="box research-box">
						<label class="research-input-title" for="region">Régions</label>
	
						<c:forEach items="${ regionsList }" var="region">
							<div class="research-input">
								<input type="checkbox" name="region" value="${region}" /><label
									class="input-text"><c:out value="${ region }" /></label>
							</div>
						</c:forEach>
					</div>
	
					<div class="box research-box">
						<label class="research-input-title" for="sectors">Nombre de
							secteurs</label> <input class="research-slider" type="range"
							name="sectors" min="0" value="${requestScope.sectors}">
					</div>
	
					<div class="box research-box">
						<label class="research-input-title" for="length">Longueur</label>
						<input class="research-slider" type="range" name="length" min="0"
							max="100" value="${requestScope.length}">
					</div>
	
					<div class="box research-box">
						<div class="research-input">
							<input type="checkbox" name="official" value="true" />
							<label class="input-text">Site Officiel Amis de l'Escalade</label>
						</div>
					</div>
	
					<input class="research-submit" type="submit" value="Rechercher">
				</form>
			</div>
	
			<div class="box-list box-list-right">
				<h2 class="results-title"><c:out value="${results.size()}" /> résultat(s)</h2>
	
				<c:forEach var="site" items="${results}">
					<div class="box result-box">
						<div class="result-left">
							<p class="site-result">
								<span class="site-name">${site.name}</span>
								<c:if test="${site.official}"> - <span class="official">Site
										Officiel</span>
								</c:if>
							</p>
							<span class="site-region">${site.region}</span>
						</div>
	
						<a href="site/${site.id}"><button class="button" type="button">Visiter</button></a>
					</div>
				</c:forEach>
			</div>
		</div>
	</body>
</html>