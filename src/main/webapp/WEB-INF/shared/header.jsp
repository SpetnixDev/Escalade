<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
	<nav class="navbar navbar-expand-lg navbar-light bg-dark">
  		<div class="container-fluid">
			<button class="navbar-toggler bg-white button" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			
			<div class="collapse navbar-collapse justify-content-center" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item p-2">
						<a class="nav-link text-white px-3" href="/Escalade/home">Accueil</a>
					</li>
					<li class="nav-item p-2">
						<a class="nav-link text-white px-3" href="/Escalade/sites">Sites</a>
					</li>
					<li class="nav-item p-2">
						<a class="nav-link text-white px-3" href="/Escalade/topos">Topos</a>
					</li>
	
					<c:choose>
						<c:when test="${empty sessionScope.user}">
							<li class="nav-item p-2">
								<a class="nav-link text-white px-3" href="/Escalade/signin">Connexion</a>
							</li>
							<li class="nav-item p-2">
								<a class="nav-link text-white px-3" href="/Escalade/signup">Inscription</a>
							</li>
						</c:when>
	
						<c:otherwise>
							<li class="nav-item p-2">
								<a class="nav-link text-white px-3" href="/Escalade/profile">${sessionScope.user.firstName}</a>
							</li>
							<li class="nav-item p-2">
								<a class="nav-link text-white px-3" href="/Escalade/logout">Déconnexion</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>
</header>