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
    <h1>Login</h1>
    
    <c:if test="${not empty requestScope.incorrectLogin}">
    	<p style="color: red;">${requestScope.incorrectLogin}</p>
    </c:if>
    
    <form method="post">
        <label for="username">Nom d'utilisateur:</label>
        <input type="text" id="username" name="username" required><br><br>
        
        <label for="password">Mot de passe:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <input type="submit" value="Se connecter">
    </form>
</body>
</html>