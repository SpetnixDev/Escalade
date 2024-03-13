<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	    <title>Page d'inscription</title>
	    <link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<h1 class="page-title">Inscription</h1>
		
		<div class="container">
		
		    <form class="login-form" method="post">
		    	<div class="form-group">
			    	<label class="input-label" for="email">Email :</label>
			    	<input class="input-field" type="email" id="email" name="email" placeholder="email" required>
		    		
					<c:if test="${not empty requestScope.emailAlreadyUsed}">
				    	<p style="color: red;">${requestScope.emailAlreadyUsed}</p>
				    </c:if>
		    	</div>
		    
		    	<div class="form-group">
			        <label class="input-label" for="lastName">Nom :</label>
			        <input class="input-field" type="text" id="lastName" name="lastName" placeholder="nom" required>
		    	</div>
		
		    	<div class="form-group">
			        <label class="input-label" for="firstName">Prénom :</label>
			        <input class="input-field" type="text" id="firstName" name="firstName" placeholder="prénom" required>
		    	</div>
		
		    	<div class="form-group">
			        <label class="input-label" for="password">Mot de passe :</label>
			        <input class="input-field" type="password" id="password" name="password" placeholder="mot de passe" required>
		    	</div>
		
		        <input class="submit" type="submit" value="S'inscrire">
		    </form>
		</div>
	</body>
</html>