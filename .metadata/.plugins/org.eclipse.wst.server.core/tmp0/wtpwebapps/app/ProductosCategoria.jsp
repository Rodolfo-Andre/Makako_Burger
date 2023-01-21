<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<title>Productos</title>
	
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
			<c:when test="${listProducto.size() == 0 }">
				<div class="container d-flex flex-column text-center p-2 rounded-3 shadow mb-4" style="max-width: 824px; border: 2px solid #FABE27">
					<div class="m-auto pt-4 px-3">
						<i class="fa-solid fa-circle-exclamation text-warning" style="font-size: 80px"></i>
						<h2 class="makako-font text-uppercase">No hay ${ categoria } por el momento, sea paciente</h2>
					</div>
	
					<div class="d-flex flex-wrap justify-content-end">
						<p class="m-0 px-2 fw-normal">¿Desea regresar al inicio?</p>
						<a class="text-decoration-none" href="indexUser.jsp">Regresar</a>
					</div>
				</div>
			</c:when>
	
			<c:otherwise>
				<div class="container my-4">
					<div class="makako-border">
						<h1 class="d-flex align-items-center title">
							<img src="https://img.icons8.com/external-flaticons-lineal-color-flat-icons/64/000000/external-monkey-animal-flaticons-lineal-color-flat-icons-3.png" class="me-2" />
							${ categoria }
						</h1>
						
						<div class="d-flex row row-cols-1 row-cols-md-2 row-cols-xl-3 justify-content-around">
							<c:forEach var="prodsList" items="${ listProducto }">
								<div class="col">
									<input type="hidden" name="IdProducto" value="${ prodsList.getCodProd() }">
	
									<figure class="m-4 d-flex flex-column align-items-center shadow-sm rounded-end">
										<img src="./img/productos/${ prodsList.getImagenProd() }.png" class="figure-img img-responsive product-img rounded-top shadow" alt="${ prodsList.getNomPro() }">
										
										<div class="p-2">
											<h4>${ prodsList.getNomPro() }</h4>
											<p class="m-0">Precio: ${ prodsList.getPrecioPro() }</p>
										</div>
										
										<button class="btnCarrito btn makako-btn mt-2 mb-3 rounded-pill">Agregar Carrito</button>
									</figure>
								</div>
							</c:forEach>
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