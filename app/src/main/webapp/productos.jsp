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
	
	<!--GOOGLE FONTS - RALEWAY-->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Chau+Philomene+One&family=Raleway:wght@500&family=Secular+One&display=swap" rel="stylesheet">
	
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
			<div class="header-info">
				<h2>Productos</h2>

				<form class="form-search" method="POST">
					<select class="form-select" name="cboCategoryProd" id="cboCategoryProd">
						<option value="all">Todos</option>
						
						<c:forEach var="categoriaProducto" items="${ listaCategoriaProductoP }">
							<option value="${ categoriaProducto.getId_CatProd() }" ${ categoriaProducto.getId_CatProd() == categoriaProdB ? 'selected' : '' }>
								${ categoriaProducto.getNombre_CatProd() }
							</option>
						</c:forEach>
					</select> 
					
					<input class="form-control" type="text" id="search-prod" name="searchProd" placeholder="Buscar..." value="${ busqueda == null ? '' : busqueda}" /> 
					<input class="form-control" type="text" id="start-price" name="startPrice" placeholder="Precio de Inicio" pattern="^\d{1,3}(.\d{1,2})?$" title="Introduce el precio correctamente. Se acepta como máximo 3 enteros y 2 decimales que son establecidos por un '.'." value="${ precioInicio == null ? '' : precioInicio }" /> 
					<input class="form-control" type="text" id="end-price" name="endPrice" placeholder="Precio Final" pattern="^\d{1,3}(.\d{1,2})?$" title="Introduce el precio correctamente. Se acepta como máximo 3 enteros y 2 decimales que son establecidos por un '.'." value="${ precioFinal == null ? '' : precioFinal }" /> 
					<input class="form-control bg-primary text-white" type="submit" value="Aplicar Búsqueda" />
				</form>

				<button id="btn-add-prod" class="btn-add">
					Añadir Producto 
					<i class="fs-4 bi bi-plus-circle-fill"></i>
				</button>
			</div>

			<hr class="separator">

			<div class="body-info">
				<c:choose>
					<c:when test="${ totalRegistro == 0 }">
						<div class="d-row">
							<div class="data">
								<span>No hay datos para mostrar</span>
							</div>
						</div>
					</c:when>

					<c:otherwise>
						<c:forEach items="${ listProductos }" var="producto">
							<div class="d-row">
								<div class="data">
									<input type="hidden" name="id" value=${ producto.getCodProd()}>
									<input type="hidden" name="object" value="prod"> 
									<span>${ producto.getCodProd() }</span>

									<div class="img-prod">
										<img alt="${ producto.getNomPro() }" src="./img/productos/${ producto.getImagenProd() }.png" />
									</div>

									<span>${ producto.getNomPro() }</span> 
									<span>${ producto.getPrecioPro() }</span>
									<span>${ producto.getCatProducto().getNombre_CatProd() }</span>
								</div>

								<div class="operations">
									<i class="icon-info bi bi-info-circle-fill"></i> 
									<i class="icon-update bi bi-pencil-square"></i> 
									<i class="icon-delete bi bi-trash-fill"></i>
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
								<a class="link-active" href="ProductosController?pProductos=${ pag - 1 }">Anterior</a>
							</c:if>

							<c:forEach var="i" begin="${ comienza }" end="${ termina }">
								<a ${ pag == i ? "class='link-desactive'" : "class='link-active' href='ProductosController?pProductos=" += i += "'" }>${ i }</a>
							</c:forEach>

							<c:if test="${ pag < totalDePaginacion }">
								<a class="link-active" href="ProductosController?pProductos=${ pag + 1 }">Siguiente</a>
							</c:if>
						</ul>
					</div>
				</c:if>

				<span>Se encontraron ${ totalRegistro } resultados</span>
			</div>
		</div>
	</main>

	<%@ include file="snippets/modal-template.jsp"%>
</body>
<script type="module" src="./js/productos.js"></script>
</html>