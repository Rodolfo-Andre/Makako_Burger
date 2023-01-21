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

	<main class="flex-grow-1 px-2 d-flex align-items-center">
		<c:choose>
			<c:when test="${cliente == null || nomUsuario.getIdTipoUsuario() == 1 }">
				<div class="container d-flex flex-column text-center p-2 rounded-3 shadow mb-4" style="max-width: 824px; border: 2px solid #FABE27">
					<div class="m-auto pt-4 px-3">
						<i class="fa-solid fa-circle-exclamation text-danger" style="font-size: 80px"></i>
						<h2 class="makako-font text-uppercase">${ nomUsuario.getIdTipoUsuario() == 1 ? "No tienes los permisos de cliente para realizar un reclamo" : "Sesión no iniciada" }</h2>
					</div>
	
					<c:choose>
						<c:when test="${ nomUsuario.getIdTipoUsuario() == 1 }">
							<div class="m-auto p-2">
								<h4>Debes de iniciar sesión como cliente.</h4>
							</div>
						</c:when>
	
						<c:otherwise>
							<div class="m-auto p-2">
								<h4>Para realizar un reclamo deberá tener una cuenta.</h4>
	
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
				<div class="container mb-4">
					<div class="m-auto makako-border">
						<div>
							<h1 class="title">Libro de Reclamaciones</h1>
						</div>
						
						<div class="mx-2 text-center">
							<h4 class="makako-font">HOJA DE RECLAMACIONES</h4>
	
							<p>
								Conforme a lo establecido en
								el código de la Protección y Defensa del consumidor este
								establecimiento cuenta con un Libro de Reclamaciones a tu
								disposición. Registra la queja o reclamo aqui.
							</p>
						</div>
	
						<div>
							<div class="p-3 m-auto" style="max-width: 824px">
								<form id="form-reclamo" action="ReclamosController" method="post">
									<input type="hidden" name="type" value="enviarReclamo">
									
									<!-- Pedido -->
									<div>
										<label class="fs-12-px">Código Pedido</label>
										
										<div id="cod-pedido-invalid" class="invalid-feedback">Código inválido.</div>
										
										<div class="d-flex py-2 pe-2 my-2">
											<span class="border-bottom px-2" id="basic-addon1">
												<i class="fs-2 fa-solid fa-regular fa-file-lines text-warning" style="vertical-align: -webkit-baseline-middle;"></i>
											</span>
											
											<input type="text" class="border-bottom form-control-plaintext" placeholder="Ingrese el código del pedido" aria-label="Nombre de usuario" aria-describedby="basic-addon1" style="outline: 0px" name="codPedido">
										</div>
									</div>
	
									<div class="d-flex flex-wrap">
										<div class="flex-grow-1">
											<label class="fs-12-px">Nombre Cliente</label>
											
											<div class="d-flex pe-2 pt-2 pb-2 my-2">
												<span class="border-bottom px-2" id="basic-addon1">
													<i class="fs-2 fa-regular fa-user text-warning"></i>
												</span>
												
												<input type="text" class="border-bottom d-block w-100 border-none form-control-plaintext" placeholder="${ cliente.getNomCliente() } ${ cliente.getApeCliente() }" aria-label="Nombre de usuario" aria-describedby="basic-addon1" style="outline: 0px" name="nomCliente" readonly>
											</div>
										</div>
	
										<div class="flex-grow-1">
											<label class="fs-12-px">Fecha</label>
											
											<div class="d-flex pe-2 pt-2 pb-2 my-2">
												<span class="border-bottom px-2" id="basic-addon1">
													<i class="fs-2 text-warning fa-regular fa-user "></i>
												</span> 
												
												<input type="text" class="border-bottom d-block w-100 border-none form-control-plaintext" placeholder="${ fechaFormateada }" aria-label="Nombre de usuario" aria-describedby="basic-addon1" style="outline: 0px" name="fechaReclamo" readonly>
											</div>
										</div>
									</div>
	
									<div>
										<label class="fs-12-px">DNI</label>
											
										<div class="d-flex py-2 pe-2 my-2">
											<span class="border-bottom px-2" id="basic-addon1">
												<i class="fs-2 fa-solid fa-solid fa-id-card text-warning"></i>
											</span>
												
											<input type="text" class="border-bottom form-control-plaintext" placeholder="${ cliente.getDni() }" aria-label="Nombre de usuario" aria-describedby="basic-addon1" style="outline: 0px" name="dniCliente" readonly>
										</div>
									</div>
	
									<div>
										<label class="fs-12-px">Tipo de Reclamo</label>
									
										<div id="rd-invalid" class="text-start invalid-feedback">Elige uno de estas opciones.</div>
									
										<div class="d-flex py-2 my-2 gap-3">
											<div class="form-check">
												<input class="form-check-input" type="radio" value="1" name="rbTipoReclamo" id="flexRadioDefault1"> 
												<label class="form-check-label" for="flexRadioDefault1">Pedido </label>
											</div>
		
											<div class="form-check">
												<input class="form-check-input" type="radio" value="2" name="rbTipoReclamo" id="flexRadioDefault2"> 
												<label class="form-check-label" for="flexRadioDefault2">Servicio </label>
											</div>
										</div>
									</div>
										
									<div class="form-floating">
										<textarea class="form-control border shadow-none" name="detReclamaco" placeholder="Comenta aquí" id="floatingTextarea2" style="height: 150px"></textarea>
										
										<label for="floatingTextarea2">Detalle Reclamación</label>
	
										<div class="invalid-feedback">
											Introduce correctamente el reclamo. No puede haber 2 espacios por
											palabras y tampoco debe de comenzar o terminar con un espacio.
											Solo se acepta un máximo de 200 caracteres.
										</div>
									</div>
									
									<div class="my-3 text-center">
										<button id="btn-add-reclamo" type="submit" class="btn makako-btn">Envíar Reclamo</button>
									</div>
									
									<c:if test="${ reclamoRegistrado == true }">
										<p class="alert alert-success">El reclamo fue enviado</p>
									</c:if>
								</form>
							</div>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</main>

	<%@ include file="snippets/footerIndexUser.jsp"%>
</body>
<script type="module" src="./js/reclamos.js"></script>
</html>