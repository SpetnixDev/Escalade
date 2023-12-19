<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Recherche de sites</title>
		<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp" %>
	
	    <h1 class="page-title">Rechercher des sites d'escalade</h1>
	
		<div class="research">
		    <div class="research-inputs">
		        <form method="post">
		            <label for="region">Région</label>
		            <input type="checkbox" name="region" value="region1"> Région 1
		            <input type="checkbox" name="region" value="region2"> Région 2
		
		            <label for="sectors">Nombre de secteurs</label>
		            <input type="range" name="sectors" min="0" value="${requestScope.sectors}">
		
		            <label for="length">Longueur</label>
		            <input type="range" name="length" min="0" max="100" value="${requestScope.length}">
		
		            <label for="official">Site officiel</label>
		            <input type="checkbox" name="official" value="true"> Oui
		
		            <input type="submit" value="Rechercher">
		        </form>
		    </div>
		    
		    <div class="results-list">
		    	<c:set var="regions" value="${requestScope.regions}" />
	 
				<c:if test="${not empty regions}">
					<p>Régions sélectionnées :</p>
						<ul>
							<c:forEach var="region" items="${regions}">
								<li>${region}</li>
							</c:forEach>
						</ul>
				</c:if>
				
				<p>Nombre de secteurs : ${requestScope.sectors}</p>
				<p>Longueur : ${requestScope.length}</p>
				<p>Officiel : ${requestScope.official}</p>
		    </div>
	    </div>
	</body>
</html>