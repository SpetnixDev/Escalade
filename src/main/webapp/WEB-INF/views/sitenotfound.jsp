<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>Site non trouvé</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	    <link href="${pageContext.request.contextPath}/resources/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<%@ include file="../shared/header.jsp" %>
		
		<div class="container">
			<h2 class="text-center my-3">Site non trouvé</h2>
			
			<p class="text-center red">Le site auquel vous essayez d'accéder n'existe pas, ou a été supprimé.</p>
			
			<div class="d-flex flex-column flex-sm-row justify-content-center col gap-2">
				<a href="home" class="mx-sm-0 mx-auto"><button class="button px-3">Retour à l'accueil</button></a>
				<a href="sites" class="mx-sm-0 mx-auto"><button class="button px-3">Retour à la liste des sites</button></a>
			</div>
		</div>
		
		<script src="${pageContext.request.contextPath}/resources/script.js"></script> 
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	</body>
</html>