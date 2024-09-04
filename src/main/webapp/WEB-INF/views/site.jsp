<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>${site.name}</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp" %>
		
		<div class="container">
			<h1 class="text-center my-3"><c:out value="${site.name}" /></h1>
			
			<h5 class="text-center mb-3"><c:out value="${site.description}" /></h5>
			
			<div class="d-flex flex-column flex-md-row gap-3">
				<div class="col-md-4 col-sm-12">
					<div class="bg-light p-2 mb-2 rounded">
						<h2 class="text-center">Informations</h2>
							
						<hr />
						
						<div class="row mb-2">
					        <div class="col-4 col-lg-3 fw-bold">Région</div>
					        <div class="col-8 col-lg-9"><c:out value="${site.region}" /></div>
					    </div>
					    
					    <div class="row mb-2">
					        <div class="col-4 col-lg-3 fw-bold">Longueur</div>
					        <div class="col-8 col-lg-9"><c:out value="${site.getLength()}" /></div>
					    </div>
					    
					    <div class="row mb-2">
					        <div class="col-4 col-lg-3 fw-bold">Cotation</div>
					        <div class="col-8 col-lg-9"><c:out value="${site.getRating()}" /></div>
					    </div>
					
						<div class="row mb-2">
					        <div class="col-4 col-lg-3 fw-bold">Secteurs</div>
					        <div class="col-8 col-lg-9"><c:out value="${site.sectors.size()}" /></div>
					    </div>
					    
					    <div class="row">
					        <div class="col-4 col-lg-3 fw-bold">Voies</div>
					        <div class="col-8 col-lg-9"><c:out value="${site.getNumberOfRoutes()}" /></div>
					    </div>
					</div>
					
					
					<c:if test="${not empty sessionScope.user && sessionScope.user.member && not site.official}">
						<div class="bg-light p-2 mb-2 rounded text-center">
							<button class="button px-3 official-button">Définir comme officiel</button>
						</div>
					</c:if>
						
						
					<c:if test="${site.official}">
						<div class="bg-light p-2 mb-2 rounded">
							<p class="red m-0">Ce site est Officiel Les Amis de L'escalade</p>	
						</div>
					</c:if>
				</div>
				
				<hr class="d-block d-md-none" />
				
				<div class="col-md-8 col-sm-12">
					<div class="p-0 mb-2 px-md-2 rounded">
						<c:forEach var="sector" items="${site.sectors}">
							<div class="bg-light rounded row py-2 m-0 mb-2 collapsible">
								<div class="d-flex justify-content-between align-items-center">
									<h3 class="m-0"><c:out value="${sector.name}" /></h3>
									
									<button class="button px-3" onclick="toggleContent(this, event)">+</button>
								</div>
								
								<div class="collapsible-content">
									<p class="m-0"><c:out value="${sector.description}" /></p>
									<p>Ce secteur contient <b>${sector.routes.size()} voie(s)</b> pour un total de <b>${sector.getFormattedLength()}</b>.</p>
									
									<c:forEach var="route" items="${sector.routes}">
										<div class="bg-lightgray rounded row py-2 m-0 mt-2 collapsible route-box">
											<div class="d-flex justify-content-between align-items-center">
												<h3 class="m-0"><c:out value="${route.name}" /></h3>
												
												<button class="button px-3" onclick="toggleContent(this, event)">+</button>
											</div>
											
											<div class="collapsible-content">
												<p class="m-0"><c:out value="${route.description}" /></p>
												<p>Cette voie contient <b>${route.pitches.size()} longueur(s)</b> pour un total de <b>${route.getFormattedLength()}</b>.</p>
												
												<div class="bg-light mb-0 d-none d-md-block border border-info px-2">
												    <div class="d-flex flex-wrap py-2 fw-bold">
												        <p class="col-3 mb-0">Nom</p>
												        <p class="col-3 col-lg-5 mb-0">Description</p>
												        <p class="col-3 col-lg-2 mb-0 text-center">Longueur</p>
												        <p class="col-3 col-lg-2 mb-0 text-center">Cotation</p>
												    </div>
												    
												    <c:forEach var="pitch" items="${route.pitches}">
												        <div class="d-flex flex-wrap py-2 border-top">
												            <p class="col-3 mb-0"><c:out value="${pitch.name}" /></p>
												            <p class="col-3 col-lg-5 mb-0"><c:out value="${pitch.description}" /></p>
												            <p class="col-3 col-lg-2 mb-0 text-center">${pitch.length}m</p>
												            <p class="col-3 col-lg-2 mb-0 text-center">${pitch.difficulty}</p>
												        </div>
												    </c:forEach>
												</div>
												
												<div class="d-block d-md-none">
													<c:forEach var="pitch" items="${route.pitches}">
														<div class="card mt-2">
															<div class="card-header">
															    <c:out value="${pitch.name}" />
															</div>
															
															<ul class="list-group list-group-flush">
															    <li class="list-group-item"><c:out value="${pitch.description}" /></li>
															    <li class="list-group-item"><b>Longueur</b> : ${pitch.length}m</li>
															    <li class="list-group-item"><b>Cotation</b> : ${pitch.difficulty}</li>
															</ul>
														</div>
													</c:forEach>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			
			<hr />
			
			<div class="comments-container d-flex flex-column gap-1 mb-3">
				<h2 class="comments-title"><c:out value="${comments.size()}" /> commentaire(s)</h2>
				
				<c:if test="${not empty sessionScope.user}">
					<div class="comment-area d-flex flex-row justify-content-between align-items-center gap-4 mb-3">
						<textarea class="flex-fill comment-field" name="commentarea" oninput="autoAdjustHeight(this)" placeholder="Ecrivez un commentaire ici..."></textarea>
						
						<button class="button px-3" id="send-comment" name="submit-comment">Envoyer</button>
					</div>
				</c:if>
				
				<c:forEach var="comment" items="${comments}">
					<div class="comment-box bg-light row m-0 py-2 px-1 rounded">
						<div class="col-12 col-sm-9">
							<p class="m-0 blue"><c:out value="${comment.user.firstName}" /> <c:out value="${comment.user.lastName}" /> <span class="comment-date">- ${comment.date}</span></p>
			                <p class="comment-content m-0"><c:out value="${comment.content}" /></p>
						</div>
						
						<c:if test="${not empty sessionScope.user && sessionScope.user.member}">
							<div class="col-12 col-sm-3 pt-2 py-sm-0 d-flex flex-row flex-sm-column justify-content-between justify-content-sm-center align-items-center align-items-sm-end gap-1">
								<button class="button edit-comment px-3 h-auto d-flex align-items-center justify-content-center" id="edit-comment" name="edit-comment" data-comment-id="${comment.id}" data-content="${comment.content}">Modifier</button>
								<button class="button red-button delete-comment px-3 h-auto d-flex align-items-center justify-content-center" id="delete-comment" name="delete-comment" data-comment-id="${comment.id}">Supprimer</button>
							</div>
						</c:if>
					</div>
        		</c:forEach>
			</div>
		</div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	</body>
</html>