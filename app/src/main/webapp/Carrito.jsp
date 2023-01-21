<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<title>Carrito de compras</title>
	
	<!--FONT AWESOME-->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	
	<!--GOOGLE FONTS - RALEWAY-->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Raleway:wght@500&display=swap" rel="stylesheet">
	
	<!--BOOTSTRAP CSS-->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
	
	<!--CUSTOM CSS-->
	<link rel="stylesheet" href="./css/user_style.css">
	
	<!--SCRIPTS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
</head>
<body class="d-flex flex-column min-vh-100">
	<%@ include file="snippets/headerIndexUser.jsp"%>

	<main class="flex-grow-1 px-2 d-flex align-items-center">
		<c:choose>
			<c:when test="${ cliente == null || nomUsuario.getIdTipoUsuario() == 1 }">
				<div class="container d-flex flex-column text-center p-2 rounded-3 shadow mb-4" style="max-width: 824px; border: 2px solid #FABE27">
					<div class="m-auto pt-4 px-3">
						<i class="fa-solid fa-circle-exclamation text-danger" style="font-size: 80px"></i>
						<h2 class="makako-font text-uppercase">${ nomUsuario.getIdTipoUsuario() == 1 ? "No tienes los permisos de cliente para realizar una compra" : "Sesión no iniciada" }</h2>
					</div>

					<c:choose>
						<c:when test="${ nomUsuario.getIdTipoUsuario() == 1 }">
							<div class="m-auto p-2">
								<h4>Debes de iniciar sesión como cliente.</h4>
							</div>
						</c:when>

						<c:otherwise>
							<div class="m-auto p-2">
								<h4>Para realizar una compra deberá tener una cuenta.</h4>

								<div class="d-flex justify-content-around flex-wrap gap-2">
									<a href="login.jsp" class="btn makako-btn">Iniciar Sesión</a> 
									<a href="Cliente.jsp" class="btn makako-btn">Registrarse</a>
								</div>
							</div>
						</c:otherwise>
					</c:choose>

					<div class="d-flex flex-wrap justify-content-end">
						<p class="m-0 px-2 fw-normal">¿Desea regresar al inicio?</p>
						<a class="text-decoration-none" href="indexUser.jsp">Regresar</a>
					</div>
				</div>
			</c:when>
			
			<c:otherwise>
				<div class="container">
					<h1>Carrito de compras</h1>
					
					<hr>
					
					<div class="d-flex flex-column align-items-start table-responsive" id="cards">
						<!--Aquí se muestra la información de las compras-->
						<table class="table text-center">
							<thead>
								<tr id="thead_tr">
									<c:choose>
										<c:when test="${ listProducto.size() <= 0 }">
											<th scope="col" class="nombreProducto">Agrega tu producto aquí</th>
										</c:when>
											
										<c:otherwise>
											<th class="p-0" scope="col"></th>
											<th scope="col" colspan="2">Nombre de Producto</th>
											<th scope="col">Precio</th>
											<th scope="col">Cantidad</th>
											<th scope="col">Pedir</th>
											<th scope="col">Total</th>
										</c:otherwise>
									</c:choose>	
								</tr>
							</thead>
							
							<tbody id="items">
								<c:forEach var="DetaPedido" items="${ listProducto }">
									<tr class="align-middle">
										<td class="p-0">
											<input type="hidden" id="id" name="id" value="${ DetaPedido.getProduct().getCodProd() } ${ DetaPedido.getPromo().getCodPromo() } ">
											<input type="hidden" id="object" name="object" value="${ DetaPedido.getProduct() == null ? 'promo' : 'producto' }">
										</td>

										<td>
											${ DetaPedido.getProduct().getNomPro() } ${ DetaPedido.getPromo().getNomPromo() }
										</td>
							
										<td style="width: 250px">
											<c:if test="${ DetaPedido.getCodProd() != 0 }">
												<img class="img-responsive cart-img" alt="${ DetaPedido.getProduct().getNomPro() }" src="./img/productos/${ DetaPedido.getProduct().getImagenProd() }.png ">
											</c:if>
											
											<c:if test="${DetaPedido.getCodPromo() != 0}">
												<img class="img-responsive cart-img" alt="${ DetaPedido.getPromo().getNomPromo() }" src="./img/promociones/${ DetaPedido.getPromo().getImagenCombo() }.png ">
											</c:if>
										</td>

										<td>
										 	${ DetaPedido.getProduct().getPrecioPro()} ${DetaPedido.getPromo().getPrecioPromo() }
										 </td>

										<td>${ DetaPedido.getCant() }</td>
										
										<td>
											<div class="d-flex justify-content-center gap-2">
												<button class="btnAgregar btn btn-info btn-sm">+</button>
												<button class="btnDesminuir btn btn-danger btn-sm">-</button>
											</div>
										</td>
										
										<td>${DetaPedido.getPrecioPedidoTot()}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					
					<button class="my-2 btn makako-btn" id="CDP">Genera Comprobante de Pago</button>
				</div>
			</c:otherwise>
		</c:choose>
	</main>

	<%@ include file="snippets/modal-template.jsp"%>
	<%@ include file="snippets/footerIndexUser.jsp"%>
</body>
<script type="module" src="./js/carrito.js"></script>
</html>