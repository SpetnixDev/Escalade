<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>Mon Profil</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp" %>
	
		<div class="container">
			<h1 class="text-center my-3">Mon Profil</h1>
			
			<div class="d-flex flex-column flex-md-row gap-3">
				<div class="col-md-4 col-sm-12">
					<div class="bg-light p-2 mb-2 rounded">
						<h2 class="text-center">Informations</h2>
						
						<hr />
						
						<div class="row mb-2">
					        <div class="col-4 col-md-3 fw-bold">Nom</div>
					        <div class="col-8 col-md-9"><c:out value="${sessionScope.user.lastName}" /></div>
					    </div>
					    
					    <div class="row mb-2">
					        <div class="col-4 col-md-3 fw-bold">Prénom</div>
					        <div class="col-8 col-md-9"><c:out value="${sessionScope.user.firstName}" /></div>
					    </div>
					    
					    <div class="row">
					        <div class="col-4 col-md-3 fw-bold">Email</div>
					        <div class="col-8 col-md-9"><c:out value="${sessionScope.user.email}" /></div>
					    </div>
					</div>
					
					<div class="bg-dark d-flex justify-content-center align-items-center p-2 rounded">	
						<a href="/Escalade/registertopo"><button class="button px-3" type="button">Ajouter un topo</button></a>
					</div>
				</div>
				
				<hr class="d-block d-md-none" />
				
				<div class="col-md-8 col-sm-12">
					<div class="p-0 mb-2 px-md-2 rounded">
						<c:choose>
							<c:when test="${not empty sessionScope.user.topos}">
								<h2 class="text-center pt-md-2">Vous possédez <c:out value="${sessionScope.user.topos.size()}" /> topo(s)</h2>
							
								<c:forEach var="topo" items="${sessionScope.user.topos}">
									<div class="bg-light rounded col p-2 mb-2">
										<div>
											<h4 class="text-center text-md-start blue"><c:out value="${topo.title}" /></h4>
											<p class="p-0 m-0"><c:out value="${topo.description}" /></p>
											<p class="p-0">Publié le <c:out value="${topo.releaseDate}" /></p>
										</div>
										
										<c:set var="reservationId" value="" />
								        <c:set var="reservationStatus" value="" />
								        
								        <c:forEach var="res" items="${reservations}">
								            <c:if test="${res.topoId == topo.id}">
								        		<c:set var="reservationId" value="${res.id}" />
								                <c:set var="reservationStatus" value="${res.status}" />
								            </c:if>
								        </c:forEach>
								        
										<div class="d-flex flex-column flex-sm-row justify-content-between col gap-2">
											<button class="button px-3 red-button remove-topo-button mx-sm-0 mx-auto" type="button" data-topo-id="${topo.id}">Supprimer le topo</button>
											
											<button class="button px-3 availability-button mx-sm-0 mx-auto" type="button" data-topo-id="${topo.id}" data-available="${topo.available}" data-reservation-id="${reservationId}" data-reservation-status="${reservationStatus}">
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
									</div>
								</c:forEach>
							</c:when>
												
							<c:otherwise>
								<h3 class="text-center red">Vous n'avez pas de topos enregistrés.</h3>
							</c:otherwise>
						</c:choose>
						
						<hr />
						
						<c:choose>
							<c:when test="${not empty requestScope.userReservations}">
								<h2 class="text-center">Vous avez <c:out value="${requestScope.userReservations.size()}" /> réservation(s) en cours</h2>
								
								<c:forEach var="reserv" items="${requestScope.userReservations}">
									<div class="bg-light rounded col p-2 mb-2">
										<div>
											<h4 class="text-center text-md-start blue"><c:out value="${reserv.topo.title}" /></h4>
											<p class="p-0 m-0"><c:out value="${reserv.topo.description}" /></p>
											<p class="p-0">Publié le <c:out value="${reserv.topo.releaseDate}" /></p>
											
											<c:choose>
								            	<c:when test="${reserv.status == 'ongoing'}">
								            		<p class="blue m-0">Réservation acceptée, contactez le propriétaire à l'adresse email : <b><c:out value="${reserv.ownerEmail}" /></b></p>
								            	</c:when>
								            	
								            	<c:otherwise>
								            		<p class="red m-0">La réservation n'a pas encore été acceptée</p>
								            	</c:otherwise>
								            </c:choose>
										</div>
									</div>
								</c:forEach>
							</c:when>
														
							<c:otherwise>
								<h3 class="text-center red">Vous n'avez pas de réservations en cours.</h3>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js" charset="utf-8"></script> 
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	</body>
</html>