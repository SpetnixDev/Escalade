<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Ajouter un site</title>
	    <link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
	    <%@ include file="../shared/header.jsp" %>
	
	    <h1 class="page-title">Ajouter un site d'escalade</h1>
	
	    <div class="container">
	        <div class="box-list box-list-center">
	            <form id="climbingSiteForm" action="RegisterSiteServlet" method="post">
	                <div class="box research-box">
	                    <table class="infos-table">
							<thead>
								<tr>
									<th colspan="2" class="infos-title">Informations sur le site</th>
								</tr>
							</thead>
							
							<tbody>
								<tr>
									<td><label class="research-input-title" for="siteName">Nom du site :</label></td>
									<td><input class="research-input" type="text" id="siteName" name="siteName" required></td>
								</tr>
								<tr>
									<td><label class="research-input-title" for="location">Localisation :</label></td>
									<td><input class="research-input" type="text" id="location" name="location" required></td>
								</tr>
								<tr>
									<td><label class="research-input-title" for="description">Description :</label></td>
									<td><input class="research-input" type="text" id="description" name="description" required></td>
								</tr>
							</tbody>
						</table>
	                </div>
	
	
					<div class="vertical-centered active">
						<h2 class="research-title">Secteurs</h2>
		                
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
															<input class="research-input quarter-length" type="text" placeholder="Longueur en mètres" id="pitchLength" name="pitchLength" required>
															<input class="research-input quarter-length" type="text" placeholder="Cotation" id="pitchDifficulty" name="pitchDifficulty" required>
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
	        </div>
	    </div>
	    
	    <script src="${pageContext.request.contextPath}/resources/script.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/siteform.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/sitedata.js"></script>
	</body>
</html>

