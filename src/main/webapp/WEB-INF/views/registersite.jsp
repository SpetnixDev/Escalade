<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
	    <title>Ajouter un site</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	    <link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
	    <%@ include file="../shared/header.jsp" %>
	
	    <div class="container">
			<h1 class="text-center my-3">Ajouter un site d'escalade</h1>
			
			<form id="climbingSiteForm" action="RegisterSiteServlet" method="post"> 
				<div class="d-flex flex-column gap-3">
					<div class="bg-light p-2 rounded">
						<h3 class="text-center mb-3">Informations sur le site</h3>
						
						<div class="px-2">
							<div class="row mb-2">
								<label for="siteName" class="col-lg-3 col-form-label">Nom du site :</label>
								
								<div class="col-lg-9">
									<input type="text" class="form-control" id="siteName" name="siteName" required>
								</div>
							</div>
							
							<div class="row mb-2">
								<label for="description" class="col-lg-3 col-form-label">Description :</label>
									
								<div class="col-lg-9">
									<input type="text" class="form-control" id="description" name="description" required>
								</div>
							</div>	
							
							<div class="row mb-2">
								<label for="location" class="col-lg-3 col-form-label">Localisation :</label>
									
								<div class="col-lg-9">
									<select class="form-select" id="location" name="location" required></select>
								</div>
							</div>	
							
							<div class="row mb-2">
								<label for="rating" class="col-lg-3 col-form-label">Cotation :</label>
									
								<div class="col-lg-9">
									<select class="form-select" id="rating" name="rating" required>
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
						</div>
					</div>
					
					<div class="d-flex flex-column gap-2" id="sectors">
						<h2 class="text-center my-3">Secteurs</h2>
						
						<hr class="m-0" />
						
						<div class="bg-light rounded row p-2 m-0 collapsible sector" data-id="1">
							<div class="d-flex flex-row gap-2 p-0 mb-2">
								<button type="button" class="button px-3 remove-button red-button" data-for="sector" onclick="removeSector(this)" disabled>X</button>
							
								<input class="form-control flex-fill" type="text" placeholder="Nom du secteur" id="sectorName" name="sectorName" required>
								
								<button class="button px-3" onclick="toggleContent(this, event)">+</button>
							</div>
							
							<input class="form-control" type="text" placeholder="Description" id="sectorDesc" name="sectorDesc" required>
						
							<div class="collapsible-content p-0 mt-2">
								<h3 class="text-center my-2">Voies</h3>
								
								<hr />
								
								<div class="d-flex flex-column gap-2" id="routes-1">
									<div class="bg-lightgray rounded row m-0 p-2 collapsible route" data-id="1" data-sector-id="1">
										<div class="d-flex flex-row gap-2 p-0 mb-2">
											<button type="button" class="button px-3 remove-button red-button" data-for="route-1" onclick="removeRoute(this)" disabled>X</button>
									
											<input class="form-control flex-fill" type="text" placeholder="Nom de la voie" id="routeName" name="routeName" required>
											
											<button class="button px-3" onclick="toggleContent(this, event)">+</button>
										</div>
										
										<input class="form-control" type="text" placeholder="Description" id="routeDesc" name="routeDesc" required>
										
										<div class="collapsible-content p-0 mt-2">
											<h4 class="text-center my-2">Longueurs</h4>
											
											<hr />
											
											<div class="d-flex flex-column gap-2" id="pitches-1">
												<div class="bg-secondary rounded row m-0 p-2 collapsible pitch" data-id="1" data-route-id="1">
													<div class="d-flex flex-column gap-2 p-0">
														<div class="d-flex flex-row gap-2 p-0">
															<button type="button" class="button px-3 remove-button red-button" data-for="pitch-1" onclick="removePitch(this)" disabled>X</button>
													
															<input class="form-control flex-fill" type="text" placeholder="Nom de la longueur" id="pitchName" name="pitchName" required>
														</div>
														
														<div class="d-flex flex-column flex-md-row gap-2">
															<div class="flex-fill">
																<input class="form-control" type="number" min="1" placeholder="Longueur en mètres" id="pitchLength" name="pitchLength" required>
															</div>
															
															<div class="flex-fill">
																<select class="form-select" id="pitchDifficulty" name="pitchDifficulty" required>
																	<option disabled selected value="">Cotation</option>
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
															
														<input class="form-control" type="text" placeholder="Description" id="pitchDesc" name="pitchDesc" required>
													</div>
												</div>
											</div>
											
											<hr />
											
											<div class="d-flex justify-content-center align-items-center">
												<button type="button" class="button px-3" onclick="addPitch(this)" data-route-id="1">Ajouter une longueur</button>
											</div>
										</div>
									</div>
								</div>
								
								<hr />
								
								<div class="d-flex justify-content-center align-items-center">
									<button type="button" class="button px-3" onclick="addRoute(this)" data-sector-id="1">Ajouter une voie</button>
								</div>
							</div>
						</div>
					</div>
					
					<hr class="m-0" />
					
					<div class="d-flex justify-content-center align-items-center">
						<button type="button" class="button px-3" onclick="addSector()">Ajouter un secteur</button>
					</div>					
					
					<hr />	
					
					<input class="button px-3 mb-3" type="submit" value="Soumettre">
				</div>
			</form>
	        
	        <!--<div class="box-list box-list-center">
	            <form id="climbingSiteForm" action="RegisterSiteServlet" method="post">
	                <div class="vertical-centered active">
		                
		                <div id="sectors">
		                	<div class="box collapsible">
								<div class="sector" data-id="1">
									<button class="button remove-button red-button" data-for="sector" onclick="removeSector(this)" disabled>X</button>
								
									<input class="research-input half-length" type="text" placeholder="Nom du secteur" id="sectorName" name="sectorName" required>
									
									<button class="button" onclick="toggleContent(this, event)">+</button>
									
									<input class="research-input block" type="text" placeholder="Description" id="sectorDesc" name="sectorDesc" required>
								</div>
								
								<div class="collapsible-content vertical-centered">
									<h3>Voies</h3>
									
									<div class="routes">
				                		<div class="box collapsible extended route-box">
											<div class="route" data-id="1" data-sector-id="1">
												<button class="button remove-button red-button" data-for="route-1" onclick="removeRoute(this)" disabled>X</button>
										
												<input class="research-input half-length" type="text" placeholder="Nom de la voie" id="routeName" name="routeName" required>
												
												<button class="button" onclick="toggleContent(this, event)">+</button>
												
												<input class="research-input block" type="text" placeholder="Description" id="routeDesc" name="routeDesc" required>
											</div>
											
											<div class="collapsible-content vertical-centered">
												<h3>Longueurs</h3>
												
												<div class="pitches">
							                		<div class="box collapsible extended pitch-box">
														<div class="pitch" data-id="1" data-route-id="1">
															<button class="button remove-button red-button" data-for="pitch-1" onclick="removePitch(this)" disabled>X</button>
													
															<input class="research-input quarter-length" type="text" placeholder="Nom de la longueur" id="pitchName" name="pitchName" required>
															<input class="research-input quarter-length" type="number" min="1" placeholder="Longueur en mètres" id="pitchLength" name="pitchLength" required>
															<select class="research-input quarter-length" id="pitchDifficulty" name="pitchDifficulty" required>
																<option disabled selected value="">Cotation</option>
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
															<input class="research-input block" type="text" placeholder="Description" id="pitchDesc" name="pitchDesc" required>
														</div>
													</div>
												</div>
													
												<button type="button" class="button" onclick="addPitch(this)" data-route-id="1">Ajouter une longueur</button>
											</div>
										</div>
									</div>
										
									<button type="button" class="button" onclick="addRoute(this)" data-sector-id="1">Ajouter une voie</button>
								</div>
							</div>
		                </div>
		                
						<button type="button" class="button" onclick="addSector()">Ajouter un secteur</button>
		                
		                <input class="submit-site-form" type="submit" value="Soumettre">
					</div>
	            </form>
	        </div>-->
	    </div>
	    
	    <script src="${pageContext.request.contextPath}/resources/script.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/siteform.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/sitedata.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	</body>
</html>

