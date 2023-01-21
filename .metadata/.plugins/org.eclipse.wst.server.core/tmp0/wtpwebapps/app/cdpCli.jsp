<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<title>Comprobantes de Pago</title>
	
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
				<div class="makako-box">
					<h1 class="makako-font text-uppercase text-center">
						<i class="fa-solid fa-people-roof"></i> Comprobantes de Pago
					</h1>
				</div>
				
				<div class="container my-2">
					<form class="d-flex flex-row flex-wrap gap-3 form-cdp">
						<input type="hidden" name="type" value="listarCDPCli" /> 
						<input class="form-control" type="text" id="start-price" name="startPrice" placeholder="Precio de Inicio" pattern="^\d{1,3}(.\d{1,2})?$" title="Introduce el precio correctamente. Se acepta como máximo 3 enteros y 2 decimales que son establecidos por un '.'." value="${ precioInicio == null ? '' : precioInicio }" /> 
						<input class="form-control" type="text" id="end-price" name="endPrice" placeholder="Precio Final" pattern="^\d{1,3}(.\d{1,2})?$" title="Introduce el precio correctamente. Se acepta como máximo 3 enteros y 2 decimales que son establecidos por un '.'." value="${ precioFinal == null ? '' : precioFinal }" /> 
						<input class="form-control" type="date" id="start-date" name="startDate" placeholder="Fecha de Inicio" min="2000-01-01" max="2030-12-31" value="${ fechaInicio == null ? '' : fechaInicio }" /> 
						<input class="form-control" type="date" id="end-date" name="endDate" placeholder="Fecha de Fin" min="2000-01-01" max="2030-12-31" value="${ fechaFinal == null ? '' : fechaFinal }" /> 
						<input class="btn makako-btn" type="submit" value="Aplicar Búsqueda" />
					</form>
	
					<div class="my-2">
						<h4>Venta Total: ${ totalVenta }</h4>
						
						<div>
							<c:choose>
								<c:when test="${ totalRegistro == 0 }">
									<div class="d-row">
										<div class="data">
											<span>No hay datos para mostrar</span>
										</div>
									</div>
								</c:when>
	
								<c:otherwise>
									<c:forEach items="${ listaCDP }" var="cdp">
										<div class="d-row justify-content-sm-between">
											<div class="data">
												<input type="hidden" name="id" value=${ cdp.getCodComprobante() }>
												
												<span>${ cdp.getCodComprobante() }</span>
												<span>${ cdp.getFchEmitido() }</span> 
												<span>${ cdp.getDni() }</span>
												<span>${ cdp.getPrecTotPedido() }</span>
											</div>
	
											<div class="operations">
												<form method="POST" action="ReportesController">
													<input type="hidden" name="type" value="reporteCDP" /> 
													<input type="hidden" name="id" value="${ cdp.getCodComprobante() }" /> 
													<input class="btn-danger btn" type="submit" value="PDF" />
												</form>
											</div>
										</div>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>
						
						<div class="navigation-info">
							<span>Navegando de ${ pag } al ${ totalDePaginacion == 0 ? '1' : totalDePaginacion }</span>
	
							<c:if test="${ totalDePaginacion > 0 }">
								<div>
									<ul class="pagination">
										<c:if test="${  pag > 1 }">
											<a class="link-active" href="CajaController?type=listarCDPCli&p=${ pag - 1 }">Anterior</a>
										</c:if>
	
										<c:forEach var="i" begin="${ comienza }" end="${ termina }">
											<a ${ pag == i ? "class='link-desactive'" : "class='link-active' href='CajaController?type=listarCDPCli&p=" += i += "'" }>${ i }</a>
										</c:forEach>
	
										<c:if test="${ pag < totalDePaginacion }">
											<a class="link-active" href="CajaController?type=listarCDPCli&p=${ pag + 1 }">Siguiente</a>
										</c:if>
									</ul>
								</div>
							</c:if>
	
							<span>Se encontraron ${ totalRegistro } resultados</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>

	<%@ include file="snippets/footerIndexUser.jsp"%>
</body>
</html>