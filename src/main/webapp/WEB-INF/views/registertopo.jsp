<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
	    <title>Enregistrer un topo</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	    <link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp" %>
				
		<div class="container">
			<h1 class="text-center my-3">Informations du topo</h1>
			
			<div class="col-md-8 offset-md-2 col-xl-6 offset-xl-3">
				<form method="post">
				    <c:if test="${not empty requestScope.registerFailed}">
				        <div id="errorDiv" class="error-message-box" role="alert">
				            <span class="red">${requestScope.registerFailed}</span>
				        </div>
				    </c:if>
				    
				    <div class="row mb-3">
				        <label for="title" class="col-lg-3 col-form-label">Titre :</label>
				        
				        <div class="col-lg-9">
				            <input type="text" class="form-control" id="title" name="title" placeholder="Titre" required>
				        </div>
				    </div>
				    
				    <div class="row mb-3">
				        <label for="description" class="col-lg-3 col-form-label">Description :</label>
				        
				        <div class="col-lg-9">
				            <input type="text" class="form-control" id="description" name="description" placeholder="Description" required>
				        </div>
				    </div>
				
				    <div class="row mb-3">
				        <label for="location" class="col-lg-3 col-form-label">Région :</label>
				        
				        <div class="col-lg-9">
				            <select class="form-select" id="location" name="location" required></select>
				        </div>
				    </div>
				
				    <div class="row mb-3">
				        <label for="releaseDate" class="col-lg-3 col-form-label">Date de sortie :</label>
				        
				        <div class="col-lg-9">
				            <input type="date" class="form-control" id="releaseDate" name="releaseDate" min="1900-01-01" required>
				        </div>
				    </div>
				
				    <div class="row">
				        <div class="d-flex justify-content-center align-items-center">
				            <button class="button px-3" type="submit">Ajouter un topo</button>
				        </div>
				    </div>
				</form>
			</div>
		</div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	</body>
</html>