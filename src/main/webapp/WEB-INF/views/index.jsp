<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>Les Amis de l'Escalade</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<div class="bgimg"></div>
	
		<%@ include file="../shared/header.jsp" %>
		
		<c:if test="${not empty sessionScope.error}">
	        <div id="errorDiv" class="error-message-box">
	            <span class="red">${sessionScope.error}</span>
	        </div>
    	</c:if>
    	
    	<div class="container">
    		<div class="text-white rounded bg-dark my-3 p-4">
    			<div class="col-md-8 px-0">
	    			<h1>LES AMIS DE L'ESCALADE</h1>
	    			
	    			<p>Description du site des Amis de l'Escalade.<br />
	    				
	    				Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
	    				 Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
	    				  Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
	    				 Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
    			</div>
    		</div>
    		
    		<div class="rounded bg-light my-3 p-4">
    			<h3>Consulter les derniers sites d'escalade ajoutés.</h3>
    			
    			<hr />
    		
    			<c:forEach var="site" items="${requestScope.sites}">
   					<div class="row">
   						<div class="col-md-10">
    						<h4 class="text-center text-md-start"><c:out value="${site.name}" /></h4>
    						
    						<p class="text-center text-md-start"><c:out value="${site.description}" /></p>
   						</div>
   						
   						<div class="col-md-2 d-flex justify-content-center align-items-center">
   							<a href="site/${site.id}"><button class="button px-3" type="button">Visiter</button></a>
   						</div>
   					</div>
   					
   					<hr />
   				</c:forEach>
    			
    			<div class="d-flex justify-content-center align-items-center">	
    				<a href="sites"><button class="button px-3" type="button">Voir tous les sites</button></a>
    			</div>
    		</div>
    	</div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	</body>
</html>