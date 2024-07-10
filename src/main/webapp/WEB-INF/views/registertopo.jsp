<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
	    <title>Enregistrer un topo</title>
	    <link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<h1 class="page-title">Informations du topo</h1>
		
		<div class="container">
		    <form class="login-form" method="post">
		    	<c:if test="${not empty requestScope.registerFailed}">
			    	<div id="errorDiv" class="error-message-box">
			            <span class="error-message">${requestScope.registerFailed}</span>
			        </div>
			    </c:if>
		    			    
		    	<div class="form-group">
			    	<label class="input-label" for="title">Titre :</label>
			    	<input class="input-field" type="text" id="title" name="title" placeholder="titre" required>
		    	</div>
		    
		    	<div class="form-group">
			        <label class="input-label" for="description">Description :</label>
			        <input class="input-field" type="text" id="description" name="description" placeholder="Description" required>
		    	</div>
		
		    	<div class="form-group">
			        <label class="input-label" for="location">Région :</label>
			        <select class="input-field" id="location" name="location"></select>
		    	</div>
		
		    	<div class="form-group">
			        <label class="input-label" for="releaseDate">Date de sortie :</label>
			        <input class="input-field" type="date" min="1800-01-01" id="releaseDate" name="releaseDate" placeholder="" required>
		    	</div>
		
		        <input class="submit" type="submit" value="Ajouter un topo">
		    </form>
		</div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
	</body>
</html>