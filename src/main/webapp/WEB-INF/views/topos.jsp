<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Recherche de topos</title>
		<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp" %>
	
		<h1 class="page-title">Recherche de topos</h1>
	
		<div class="research">
	    	<div class="research-inputs">
	        	<form method="post">
	        		<h2 class="research-title">Recherche</h2>
	        	
	            	<label for="region">Régions :</label>
	            
		            <c:forEach items="${ regionsList }" var="region">
	    				<div class="research-input"><input type="checkbox" name="region" value="${region}" /><label class="input-text"><c:out value="${ region }" /></label></div>
					</c:forEach>
					
					<input class="searchbar" type="text" name="searchbar" placeholder="Mots-clés..." value="">
		            
		            <input class="research-submit" type="submit" value="Rechercher">
		        </form>
		    </div>
		    
			<div class="results-list">
				<h2 class="results-title"><c:out value="${results.size()}" /> topos trouvé(s)</h2>
			
				<c:forEach var="topo" items="${results}">
					<div class="topo">
						<div class="topo-left">
							<span class="topo-title">${topo.title}</span>
							<span class="topo-description">${topo.description}</span>
							
							<c:choose>
								<c:when test="${topo.owner != sessionScope.user.username}">
									<c:choose>
										<c:when test="${topo.available}">
											<span class="available">Disponible</span>
										</c:when>
										
										<c:otherwise>
											<span class="not-available">Non disponible</span>
										</c:otherwise>
									</c:choose>
								</c:when>
								
								<c:otherwise>
									<span class="owned">Ce topo est à vous</span>
								</c:otherwise>
							</c:choose>
						</div>
						
						<c:if test="${topo.owner != sessionScope.user.username && topo.available}">
							<button class="button" type="button">Réserver</button>
						</c:if>
					</div>
				</c:forEach>
			</div>
	    </div>
	</body>
</html>