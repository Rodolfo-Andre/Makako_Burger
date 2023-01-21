<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<title>Actualizar Promoción</title>
	
	<!--GOOGLE FONTS - RALEWAY-->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Raleway:wght@500&display=swap" rel="stylesheet">
	
	<!--BOOTSTRAP CSS-->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
	
	<!--BOOTSTRAP ICONS-->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">	
	
	<!--CUSTOM CSS-->
	<link rel="stylesheet" type="text/css" href="./css/style.css">
	
	<!--SCRIPTS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
</head>
<body class="d-flex">
	<c:if
		test="${ nomUsuario == null || nomUsuario.getIdTipoUsuario() == 2 }">
		<c:redirect url="indexUser.jsp"></c:redirect>
	</c:if>
	
	<%@ include file="snippets/navAdmin.jsp"%>
	
	<main class="main">
		<div class="box">
			<div class="d-flex flex-column justify-content-center align-items-center header-info gap-3">
				<c:choose>
					<c:when test="${ promocion != null }">
						<input id="idPromoAct" type="hidden" name="id" value="${ promocion.getCodPromo() }" />
						
						<h2 class="mb-0"> Nombre de la Promoción: ${ promocion.getNomPromo() } </h2>
							
						<img class="img-prod" src="./img/promociones/${ promocion.getImagenCombo() }.png" />
						
						<button id="btn-update" class="btn-add">Actualizar promoción</button>
					</c:when>
				</c:choose>
			</div>
		</div>
		
		<div class="box">
			<h2>Productos de la promoción</h2>
			
			<button id="btn-add-prod" class="btn-add">Agregar producto</button>
			
			<br>
			
			<c:forEach items="${ detalle }" var="detallePromo">
				<div class="d-row">
					<div class="data">
						<input type="hidden" name="idCatProd" value="${ detallePromo.getProd().getId_CatProd() }" /> 
						<input type="hidden" name="idProd" value="${ detallePromo.getCodPro() }" />

						<div class="img-prod">
							<img alt="${ detallePromo.getProd().getNomPro() }" src="./img/productos/${ detallePromo.getProd().getImagenProd() }.png" />
						</div>
						
						<span>${ detallePromo.getCodPro() }</span> <span>${ detallePromo.getProd().getNomPro() }</span>
						<span>${ detallePromo.getCantidad() }</span>
					</div>

					<div class="operations">
						<i class="icon-update bi bi-pencil-square"></i> 
						<i class="icon-delete bi bi-trash-fill"></i>
					</div>
				</div>
			</c:forEach>
			
			<br> 
			
			<a class="text-decoration-none" href="PromocionesController?p=1">
				<button id="btn-update" class="btn-add">Volver al listado de promociones</button>
			</a>
		</div>
	</main>

	<%@ include file="snippets/modal-template.jsp"%>
</body>
<script type="module" src="./js/detallePromo.js"></script>
</html>