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
	        		<h2 class="research-title">Recherche</h2>
	        		
		            <label for="region">Régions :</label>
		            
		            <c:forEach items="${ regionsList }" var="region">
	    				<div class="research-input"><input type="checkbox" name="region" value="${region}" /><label class="input-text"><c:out value="${ region }" /></label></div>
					</c:forEach>
		
		            <label for="sectors">Nombre de secteurs</label>
		            <input type="range" name="sectors" min="0" value="${requestScope.sectors}">
		
		            <label for="length">Longueur</label>
		            <input type="range" name="length" min="0" max="100" value="${requestScope.length}">
		
		            <label for="official">Site officiel</label>
		            <input type="checkbox" name="official" value="true">
		
		            <input type="submit" value="Rechercher">
		        </form>
		    </div>
		    
		    <div class="results-list">
		    	<h2 class="results-title"><c:out value="${results.size()}" /> site(s) trouvé(s)</h2>
		    
		    	<c:forEach var="site" items="${results}">
					<div class="result-box">
						<a href="site/${site.id}" class="site-name">${site.name}</a>
						<span class="site-region">${site.region}</span>
					</div>
				</c:forEach>
		    </div>
	    </div>
	</body>
</html>