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
	<title>Promociones</title>
	
	<!--FONT AWESOME-->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	
	<!--GOOGLE FONTS - RALEWAY-->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Chau+Philomene+One&family=Raleway:wght@500&family=Secular+One&display=swap" rel="stylesheet">
	
	<!--BOOTSTRAP CSS-->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
	
	<!--BOOTSTRAP ICONS-->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">	

	<!--CUSTOM CSS-->
	<link rel="stylesheet" href="./css/user_style.css">
	
	<!--SCRIPTS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
</head>
<body class="d-flex flex-column min-vh-100">
	<%@ include file="snippets/headerIndexUser.jsp"%>
	
	<main class="flex-grow-1 px-2 d-flex align-items-center">
		<c:choose>
			<c:when test="${ listPromo.size() == 0 }">
				<div class="container d-flex flex-column text-center p-2 rounded-3 shadow mb-4" style="max-width: 824px; border: 2px solid #FABE27">
					<div class="m-auto pt-4 px-3">
						<i class="fa-solid fa-circle-exclamation text-warning" style="font-size: 80px"></i>
						<h2 class="makako-font text-uppercase">No hay promociones por el momento, sea paciente</h2>
					</div>
	
					<div class="d-flex flex-wrap justify-content-end">
						<p class="m-0 px-2 fw-normal">¿Desea regresar al inicio?</p>
						<a class="text-decoration-none" href="indexUser.jsp">Regresar</a>
					</div>
				</div>
			</c:when>
			
			<c:otherwise>
				<div class="container mb-4">
					<div class="makako-border">
						<h1 class="title">Promociones</h1>
		
						<div class="container">
							<div>
								<c:forEach var="listaPromo" items="${ listPromo }">
									<div class="d-flex rounded-2 m-3 shadow flex-column flex-md-row">
										<div>
											<img class="img-responsive promotion-img border-2 shadow" alt="${ listaPromo.getNomPromo() }" src="./img/promociones/${ listaPromo.getImagenCombo() }.png">
										</div>
		
										<div class="d-flex flex-column flex-grow-1 justify-content-center px-3">
											<div class="mt-3">
												<h1 class="makako-font text-uppercase">${ listaPromo.getNomPromo() }</h1>
												
												<div>
													<p class="mb-2 fw-semibold text-justify">Esta promocion tendrá vigencia hasta: ${ listaPromo.getFechaVigencia() }</p> 
													<p class="mb-2 fw-semibold">Precio: ${ listaPromo.getPrecioPromo() }</p>
												</div>
											</div>
											
											<div>
												<p class="mt-2 mb-1 fs-5 makako-font text-uppercase">Productos</p>
												
												<ul>
													<c:forEach var="listDetallePromo" items="${ listDetallePromo }">
														<c:if test="${ listDetallePromo.getCodPromo() == listaPromo.getCodPromo() }">
															<li class="fs-6">${ listDetallePromo.getProd().getNomPro() }</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
											
											<div class="mb-3 text-center">
												<input type="hidden" name="IdPromo" value="${ listaPromo.getCodPromo() }">
												<button class="btnCarrito btn makako-btn rounded-pill">Agregar Carrito</button>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</main>

	<%@ include file="snippets/footerIndexUser.jsp"%>
	<%@ include file="snippets/modal-template.jsp"%>
</body>
<script type="module" src="./js/agregarproducto.js"></script>
</html>