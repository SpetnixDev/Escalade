<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Mon Profil</title>
		<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp" %>
		
		<h1 class="page-title">Mon Profil</h1>
	
		<div class="container">
			<div class="box-list box-list-left">
				<div class="box site-infos">
					<table class="infos-table">
						<thead>
							<tr>
								<th colspan="2" class="infos-title">Informations</th>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td><div>Nom</div></td>
								<td>${sessionScope.user.lastName}</td>
							</tr>
							<tr>
								<td><div>Prénom</div></td>
								<td>${sessionScope.user.firstName}</td>
							</tr>
							<tr>
								<td><div>Email</div></td>
								<td>${sessionScope.user.email}</td>
							</tr>
						</tbody>
					</table>
				</div>
					
				<a class="button-link" href="#"><button class="button button-profile" type="button">Modifier les informations</button></a>
				<a class="button-link" href="/Escalade/registertopo"><button class="button button-profile" type="button">Ajouter un topo</button></a>
			</div>
			
			<div class="box-list box-list-right">
				<c:choose>
					<c:when test="${not empty sessionScope.user.topos}">
						<h2 class="results-title">Vous possédez <c:out value="${sessionScope.user.topos.size()}" /> topo(s)</h2>
				
						<c:forEach var="topo" items="${sessionScope.user.topos}">
						    <div class="box result-box">
						        <div class="result-left">
						            <span class="topo-title">${topo.title}</span>
						            <span class="topo-description">${topo.description}</span>
						            <span class="topo-description">Publié le ${topo.releaseDate}</span>
						            <button class="button half-sized remove-topo-button red-button" type="button" data-topo-id="${topo.id}">Supprimer le topo</button>
						        </div>
						              
						        <c:set var="reservationId" value="" />
						        <c:set var="reservationStatus" value="" />
						        
						        <c:forEach var="res" items="${reservations}">
						            <c:if test="${res.topoId == topo.id}">
						        		<c:set var="reservationId" value="${res.id}" />
						                <c:set var="reservationStatus" value="${res.status}" />
						            </c:if>
						        </c:forEach>
						        
						        <button class="button availability-button" type="button" data-topo-id="${topo.id}" data-available="${topo.available}" data-reservation-id="${reservationId}" data-reservation-status="${reservationStatus}">
						            <c:choose>
						                <c:when test="${not topo.available}">
						                    <c:choose>
						                        <c:when test="${reservationStatus == 'pending'}">Réservation en attente</c:when>
						                        <c:when test="${reservationStatus == 'ongoing'}">Mettre fin à la réservation</c:when>
						                        <c:otherwise>Rendre disponible</c:otherwise>
						                    </c:choose>
						                </c:when>
						                <c:otherwise>Rendre indisponible</c:otherwise>
						            </c:choose>
						        </button>
						    </div>
						</c:forEach>
					</c:when>
												
					<c:otherwise>
						<p style="color: red;">Vous n'avez pas de topos enregistrés.</p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
	</body>
</html>