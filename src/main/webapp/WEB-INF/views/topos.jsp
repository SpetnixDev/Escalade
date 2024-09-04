<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>Recherche de topos</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp" %>
	
		<div class="container">
			<h1 class="text-center my-3">Topos</h1>
			
			<div class="d-flex flex-column flex-md-row gap-3">
				<div class="col-md-4 col-sm-12">
					<div class="bg-light p-2 mb-2 rounded">
						<h2 class="text-center">Recherche</h2>
						
						<hr />
						
						<form method="post">
			        		<div class="px-2">
								<label class="text-center w-100 pb-2" for="region">Régions</label>
		
								<c:forEach items="${regionsList}" var="region">
									<div>
										<input type="checkbox" name="region" value="${region}" />
										
										<label class="px-2"><c:out value="${region}" /></label>
									</div>
								</c:forEach>
							</div>
							
							<hr />
							
							<div>
								<input class="w-100" type="text" name="searchbar" placeholder="Mots-clés..." value="${null}">
							</div>
							
							<hr />
							
							<div class="d-flex justify-content-center align-items-center">	
								<input class="button px-3" type="submit" value="Rechercher">
							</div>
				        </form>
					</div>
				</div>
				
				<hr class="d-block d-md-none" />
			
				<div class="col-md-8 col-sm-12">
					<div class="p-0 mb-2 px-md-2 rounded">
						<h2 class="text-center pt-md-2"><c:out value="${results.size()}" /> résultat(s)</h2>
							
						<hr class="d-none d-md-block" />
						
						<c:forEach var="topo" items="${results}">
							<div class="bg-light rounded row py-2 m-0 mb-2">
								<div class="col-md-9">
									<h4 class="text-center text-md-start blue">${topo.title}</h4>
									
									<p class="p-0 m-0">${topo.description}</p>
									<p class="p-0 m-0">Publié le <b>${topo.releaseDate}</b>.</p>
									
									<c:choose>
										<c:when test="${topo.ownerId != sessionScope.user.id}">
											<c:choose>
												<c:when test="${topo.available}">
													<p class="text-success m-0">Disponible</p>
												</c:when>
												
												<c:otherwise>
													<p class="text-danger m-0">Non disponible</p>
												</c:otherwise>
											</c:choose>
										</c:when>
										
										<c:otherwise>
											<p class="blue m-0">Ce topo vous appartient</p>
										</c:otherwise>
									</c:choose>
								</div>
						
								<div class="col-md-3 d-flex justify-content-center align-items-center">
									<c:if test="${sessionScope.user != null && topo.ownerId != sessionScope.user.id && topo.available}">
										<button class="button px-3 reservation-button" type="button" data-topo-id="${topo.id}">Réserver</button>
									</c:if>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
	    </div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	</body>
</html>