<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
	<nav>
		<ul>
			<li><a href="/Escalade/home">Accueil</a></li>
			<li><a href="/Escalade/sites">Sites</a></li>
			<li><a href="/Escalade/topos">Topos</a></li>
			
			<c:choose>
				<c:when test="${empty sessionScope.user}">
					<li><a href="/Escalade/signin">Connexion</a></li>
					<li><a href="/Escalade/signup">Inscription</a></li>
				</c:when>
				
				<c:otherwise>
					<li><a href="/Escalade/profile">${sessionScope.user.firstName}</a></li>
					<li><a href="/Escalade/logout">Déconnexion</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>
</header>