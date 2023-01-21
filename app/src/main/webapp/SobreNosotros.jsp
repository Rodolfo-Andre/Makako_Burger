<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>SobreNosotros</title>
	
	<!--FONT AWESOME-->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	
	<!--GOOGLE FONTS - RALEWAY-->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Chau+Philomene+One&family=Raleway:wght@500&family=Secular+One&display=swap" rel="stylesheet">
	
	<!--BOOTSTRAP CSS-->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
	
	<!--CUSTOM CSS-->
	<link rel="stylesheet" href="./css/user_style.css">
	
	<!--SCRIPTS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
</head>
<body class="d-flex flex-column min-vh-100">
	<%@ include file="snippets/headerIndexUser.jsp"%>
	
	<main class="flex-grow-1 d-flex align-items-center">
		<div class="container mb-4">
			<div class="makako-border">
				<h1 class="title">
					<i class="fa-solid fa-people-roof nosotros"></i> 
					Sobre Nosotros
				</h1>
	
				<div class="container p-3 m-auto" style="max-width: 814px;">
					<c:forEach var="listarEstablecimiento" items="${ ListarEstablecimiento }">
						<div class="my-3 mx-auto ">
							<h2 class="makako-font text-uppercase border-bottom p-2">¿Donde nos ubicamos?</h2>
							<h4 class="text-justify">Nos puedes encontrar en ${ listarEstablecimiento.getZona().getNombreZona() }</h4>
						</div>
						
						<div class="my-3 mx-auto ">
							<h2 class="makako-font text-uppercase border-bottom p-2">¿Qué es Makako Burger?</h2>
							<h4 class="text-justify">${ listarEstablecimiento.getSobreNosotros() }</h4>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</main>
	
	<%@ include file="snippets/footerIndexUser.jsp"%>
</body>
</html>