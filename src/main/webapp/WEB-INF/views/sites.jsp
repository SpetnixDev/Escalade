<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>Recherche de sites</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<link href="${pageContext.request.contextPath}/resources/style.css"
			rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp"%>
	
		<div class="container">
			<h1 class="text-center my-3">Sites d'escalade</h1>
			
			<div class="d-flex flex-column flex-md-row gap-3">
				<div class="col-md-4 col-sm-12">
					<div class="bg-light p-2 mb-2 rounded">
						<h2 class="text-center">Recherche</h2>
						
						<hr />
					
						<form method="post">
							<div class="px-2">
								<label class="text-center w-100 pb-2" for="region">Régions</label>
		
								<c:forEach items="${regions}" var="region">
									<div>
										<input type="checkbox" name="region" value="${region}" />
										
										<label class="px-2"><c:out value="${region}" /></label>
									</div>
								</c:forEach>
							</div>
							
							<hr />
							
							<div class="px-2">
								<label class="text-center w-100 pb-2" for="sectors">Nombre de secteurs</label> 
								
								<div class="d-flex justify-content-between align-items-center">
									<input type="checkbox" name="sectorsNumbersEnabled" value="true" />
									
									<input class="px-3 mx-2 w-75" type="range" list="sectorsNumbers" name="sectors"
										 min="${minSectors}" max="${maxSectors}" value="${sectors}"
										  oninput="this.nextElementSibling.value = this.value">
										  
									<output>${sectors}</output>
								</div>
							</div>
					
							<datalist id="sectorsNumbers">
							  	<option value="${minSectors}"></option>
							  	<option value="${maxSectors}"></option>
							</datalist>
							
							<hr />
							
							<div class="px-2">
								<label class="text-center w-100 pb-2" for="rating">Cotation</label> 
								
								<div class="d-flex column-gap-2 justify-content-between align-items-center">
									<input type="checkbox" name="ratingEnabled" value="true" />
									
									<select class="flex-grow-1" name="rating">
							            <option>3</option>
							            <option>4</option>
							            <option>5a</option>
							            <option>5b</option>
							            <option>5c</option>
							            <option>6a</option>
							            <option>6a+</option>
							            <option>6b</option>
							            <option>6b+</option>
							            <option>6c</option>
							            <option>6c+</option>
							            <option>7a</option>
							          	<option>7a+</option>
							          	<option>7b</option>
							          	<option>7b+</option>
							          	<option>7c</option>
							          	<option>7c+</option>
							          	<option>8a</option>
							          	<option>8a+</option>
							          	<option>8b</option>
							          	<option>8b+</option>
							          	<option>8c</option>
							          	<option>8c+</option>
							          	<option>9a</option>
									</select>
								</div>
							</div>
							
							<hr />
	
							<div class="px-2">
								<div>
									<input type="checkbox" name="official" value="true" />
									
									<label class="px-2">Site Officiel Amis de l'Escalade</label>
								</div>
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
						
					<div class="bg-dark d-flex justify-content-center align-items-center p-2 rounded">	
						<a href="/Escalade/registersite"><button class="button px-3" type="button">Ajouter un site</button></a>
					</div>
				</div>
					
				<div class="col-md-8 col-sm-12">
					<div class="p-0 mb-2 px-md-2 rounded">
						<h2 class="text-center pt-md-2"><c:out value="${results.size()}" /> résultat(s)</h2>
						
						<hr />
					
						<c:forEach var="site" items="${results}">
							<div class="bg-light rounded row py-2 m-0 mb-2">
								<div class="col-md-9">
									<h4 class="text-center text-md-start blue">${site.name}</h4>
									
									<c:if test="${site.official}"><p class="red text-center text-md-start">Site Officiel Amis de l'Escalade</p></c:if>
									
									<p class="p-0 m-0">${site.description}</p>
									<p class="p-0 m-0">${site.region} - Ce site contient <b>${site.sectors.size()} secteur(s)</b> - Cotation : <b>${site.rating}</b></p>
								</div>
								
								<div class="col-md-3 d-flex justify-content-center align-items-center">
									<a href="site/${site.id}"><button class="button px-3 mt-md-0 mt-3" type="button">Visiter</button></a>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	</body>
</html>