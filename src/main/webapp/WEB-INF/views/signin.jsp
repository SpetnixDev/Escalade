<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
</head>
	<body>
	    <h1 class="page-title">Login</h1>
	    
	    <div class="container">
		    <form class="login-form" method="post">
			    <c:if test="${not empty requestScope.incorrectLogin}">
			    	<div id="errorDiv" class="error-message-box">
			            <span class="error-message">${requestScope.incorrectLogin}</span>
			        </div>
			    </c:if>
			    
			    <c:if test="${not empty sessionScope.error}">
			    	<div id="errorDiv" class="error-message-box">
			            <span class="error-message">${sessionScope.error}</span>
			        </div>
			    </c:if>
			    
		    	<div class="form-group">
			        <label class="input-label" for="email">Email :</label>
			        <input class="input-field" type="email" id="email" name="email" placeholder="email" required>
		        </div>
		        
		        <div class="form-group">
			        <label class="input-label" for="password">Mot de passe :</label>
			        <input class="input-field" type="password" id="password" name="password" placeholder="mot de passe" required>
		        </div>
		        
		        <input class="submit" type="submit" value="Se connecter">
		    </form>
	    </div>
	    	
	    <a class="link" href="/Escalade/signup">Créer un compte</a>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
	</body>
</html>