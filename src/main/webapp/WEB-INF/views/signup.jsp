<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
	    <title>Page d'inscription</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	    <link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp" %>
		
		<div class="container">
			<h1 class="text-center my-3">S'inscrire</h1>
			
			<div class="col-md-8 offset-md-2 col-xl-6 offset-xl-3">
				<form method="post">
					<div class="row mb-3">
				        <label for="title" class="col-lg-3 col-form-label">Email :</label>
				        
				        <div class="col-lg-9">
				            <input type="email" class="form-control" id="email" name="email" placeholder="Email" required>
				        </div>
				        
				        <c:if test="${not empty requestScope.emailAlreadyUsed}">
					    	<div id="errorDiv" class="error-message-box">
					            <span class="red">${requestScope.emailAlreadyUsed}</span>
					        </div>
					    </c:if>
				    </div>
				    
				    <div class="row mb-3">
				        <label for="lastName" class="col-lg-3 col-form-label">Nom :</label>
				        
				        <div class="col-lg-9">
				            <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Nom" required>
				        </div>
				    </div>
				
				    <div class="row mb-3">
				        <label for="firstName" class="col-lg-3 col-form-label">Prénom :</label>
				        
				        <div class="col-lg-9">
				            <input type="text" class="form-control" id="firstName" name="firstName" placeholder="Prénom" required>
				        </div>
				    </div>
				
				    <div class="row mb-3">
				        <label for="password" class="col-lg-3 col-form-label">Mot de passe :</label>
				        
				        <div class="col-lg-9">
				            <input type="password" class="form-control" id="password" name="password" placeholder="Mot de passe" required>
				        </div>
				    </div>
				
				    <div class="row">
				        <div class="d-flex justify-content-center align-items-center">
				            <button class="button px-3" type="submit">S'inscrire</button>
				        </div>
				    </div>
				</form>
			
		    	<div class="text-center mt-2">
		    		<a href="/Escalade/signin">Se connecter</a>
		    	</div>
			</div>
		</div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	</body>
</html>