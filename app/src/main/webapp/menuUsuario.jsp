<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<title>Makako Burger Café</title>
	
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
	
	<main class="flex-grow-1">
		<div class="container">
			<div class="border-bottom">
				<h1 class="d-flex align-items-center makako-font">
					<img src="https://img.icons8.com/external-flaticons-lineal-color-flat-icons/64/000000/external-monkey-animal-flaticons-lineal-color-flat-icons-3.png" class="me-2" />
					MENÚ MAKAKO
				</h1>
			</div>

			<div class="d-flex flex-wrap justify-content-around container my-4">
				<div style="max-width: 500px">
					<figure class="figure m-4 text-center">
						<img src="./img/categoria_hamburguesas.jpg" class="figure-img img-fluid rounded shadow" alt="...">
						<a href="PedidosController?type=ListarProducto&Id_CatProd=1" class="text-decoration-none btn makako-btn rounded-pill">
							Ver Todo
						</a>
					</figure>

					<figure class="figure m-4 text-center">
						<img src="./img/categoria_salchipapas.jpg" class="figure-img img-fluid rounded shadow" alt="...">
						<a href="PedidosController?type=ListarProducto&Id_CatProd=2" class="text-decoration-none btn makako-btn rounded-pill">
							Ver Todo
						</a>
					</figure>

					<figure class="figure m-4 text-center">
						<img src="./img/categoria_alitas.jpg" class="figure-img img-fluid rounded shadow" alt="...">
						<a href="PedidosController?type=ListarProducto&Id_CatProd=3" class="text-decoration-none btn makako-btn rounded-pill">
							Ver Todo
						</a>
					</figure>
				</div>

				<div style="max-width: 500px">
					<figure class="figure m-4 text-center">
						<img src="./img/categoria_bebidas.jpg" class="figure-img img-fluid rounded shadow" alt="...">
						<a href="PedidosController?type=ListarProducto&Id_CatProd=5" class="text-decoration-none btn makako-btn rounded-pill">
							Ver Todo
						</a>
					</figure>

					<figure class="figure m-4 text-center">
						<img src="./img/categoria_platos_a_la_carta.jpg" class="figure-img img-fluid rounded shadow" alt="...">
						<a href="PedidosController?type=ListarProducto&Id_CatProd=6" class="text-decoration-none btn makako-btn rounded-pill">
							Ver Todo
						</a>
					</figure>

					<figure class="figure m-4 text-center">
						<img src="./img/categoria_salchipapas.jpg" class="figure-img img-fluid rounded shadow" alt="...">
						<a href="PedidosController?type=ListarProducto&Id_CatProd=4" class="text-decoration-none btn makako-btn rounded-pill">
							Ver Todo
						</a>
					</figure>
				</div>
			</div>
		</div>		
	</main>
	
	<%@ include file="snippets/footerIndexUser.jsp"%>
</body>
</html>