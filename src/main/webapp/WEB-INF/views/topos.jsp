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
	
		<h1 class="page-title">Topos</h1>
	
		<div class="container">
	    	<div class="box-list box-list-left">
	        	<form method="post">
	        		<h2 class="research-title">Recherche</h2>
	        	
	        		<div class="box research-box">
		            	<label class="research-input-title" for="region">Régions</label>
		            
			            <c:forEach items="${ regionsList }" var="region">
		    				<div class="research-input"><input type="checkbox" name="region" value="${region}" /><label class="input-text"><c:out value="${ region }" /></label></div>
						</c:forEach>
	        		</div>
					
					<div class="box research-box">
						<input class="searchbar" type="text" name="searchbar" placeholder="Mots-clés..." value="${null}">
					</div>
		            
		            <input class="submit" type="submit" value="Rechercher">
		        </form>
		    </div>
		    
			<div class="box-list box-list-right">
				<h2 class="results-title"><c:out value="${results.size()}" /> résultat(s)</h2>
			
				<c:forEach var="topo" items="${results}">
					<div class="box result-box">
						<div class="result-left">
							<span class="topo-title">${topo.title}</span>
							<span class="topo-description">${topo.description}</span>
							
							<c:choose>
								<c:when test="${topo.ownerId != sessionScope.user.id}">
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
									<span class="owned">Ce topo vous appartient</span>
								</c:otherwise>
							</c:choose>
						</div>
						
						<c:if test="${sessionScope.user != null && topo.ownerId != sessionScope.user.id && topo.available}">
							<button class="button reservation-button" type="button" data-topo-id="${topo.id}">Réserver</button>
						</c:if>
					</div>
				</c:forEach>
			</div>
	    </div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
	</body>
</html>