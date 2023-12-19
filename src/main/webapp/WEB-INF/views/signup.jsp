<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>Page d'inscription</title>
	    <link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
	    <h2>Inscription</h2>
	    <form action="${pageContext.request.contextPath}/registration" method="post">
	    	<label for="email">Email :</label>
	    	<input type="email" id="email" name="email" required><br>
	    
	        <label for="username">Nom :</label>
	        <input type="text" id="lastName" name="lastName" required><br>
	
	        <label for="fullName">Prénom :</label>
	        <input type="text" id="firstName" name="firstName" required><br>
	
	        <label for="password">Mot de passe :</label>
	        <input type="password" id="password" name="password" required><br>
	
	        <input type="submit" value="S'inscrire">
	    </form>
</body>
</html>