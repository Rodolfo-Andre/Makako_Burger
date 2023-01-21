<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<title>Actualizar hojas de envío</title>
	
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
	
	<!--SELECT2 CSS-->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" />
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/css/select2.min.css" />
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2-bootstrap-5-theme@1.3.0/dist/select2-bootstrap-5-theme.min.css" />
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2-bootstrap-5-theme@1.3.0/dist/select2-bootstrap-5-theme.rtl.min.css" />
	
	<!--SCRIPTS-->
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.0/dist/jquery.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/select2.full.min.js"></script>
</head>
<body class= "d-flex">
	<c:if test="${ nomUsuario == null || nomUsuario.getIdTipoUsuario() == 2 }">
		<c:redirect url="indexUser.jsp"></c:redirect>
	</c:if>

	<%@ include file="snippets/navAdmin.jsp" %>
	
	<main class="main">
		<div class="box">
	    	<div class="header-info">
	       		<h2>Actualizar hojas de envío</h2>
	
	          	<form class="form-search align-items-center" method="POST">
	           		<select class="form-select p-2" name="cboEstado" id="cboPosition">
	              		<option value="all">Todos</option>
	                  	<c:forEach var="Estado" items="${ listaEstados }">
	                    	<option value="${ Estado.getCodEstadoPedido() }" ${ Estado.getCodEstadoPedido()==estadoB ? 'selected' : '' }>
	                           	${ Estado.getNomTipoEstado() }
	                       	</option>
	                   	</c:forEach>
	               	</select>
	               
	               	<select class="form-select" name="busqueda" id="busqueda">
	               		<option value="all">Todos</option>
	               		<c:forEach var="z" items="${ listaZonas }">
	                    	<option value="${ z.getNombreZona() }" 
	                       	${ z.getNombreZona()== busqueda ? 'selected' : '' }>${ z.getNombreZona() }
	                       	</option>
	                   	</c:forEach>
	             	</select>
	          
	               <input class="form-control bg-primary text-white" type="submit" value="Aplicar Búsqueda" />
	           </form>
	       	</div>
	       
	      	<hr class="separator" style="height: 0.5px">
	
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
	                	<c:forEach items="${listaHoja}" var="Hoja">
	                    	<div class="d-row">
	                       		<div class="data">
	                            	<input type="hidden" name="id" value=${ Hoja.getCodPedido() }>
	                               	<input type="hidden" name="codEstado" value=${ Hoja.getCodEstadoPedido() }>
	
	                               	<span>${ Hoja.getCodHojaEnvio() }</span>
	                               	<span>${ Hoja.getZona().getDistri().getNomDistrito() }</span>
	                               	<span>${ Hoja.getZona().getNombreZona() }</span>       
	                               	<span>${ Hoja.getTelefono() }</span>
	                               	<span>${ Hoja.getEstados().getNomTipoEstado() }</span>
	                        	</div>
	
	                           	<div class="operations">
	                               	<i class="icon-info bi bi-info-circle-fill"></i>
	                           		<i class="icon-update bi bi-pencil-square"></i>
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
		                	<c:if test="${ pag > 1 }">
		                    	<a class="link-active" href='HojaEnvioController?p=${ pag - 1 }'>Anterior</a>
		                   </c:if>
		                   
		                   <c:forEach var="i" begin="${ comienza }" end="${ termina }">
		                   		<a ${ pag==i ? "class='link-desactive'" : "class='link-active' href='HojaEnvioController?p=" +=i +="'" }>${ i }</a>
		                   </c:forEach>
	
		                   <c:if test="${ pag < totalDePaginacion }">
		                       	<a class="link-active" href='HojaEnvioController?p=${ pag + 1 }'>Siguiente</a>
		                   </c:if>
		               	</ul>
		         	</div>
	         	</c:if>
	
	          	<span>Se encontraron ${ totalRegistro } resultados</span>
	     	</div>
		</div>
	</main>
	
	<%@ include file="snippets/modal-template.jsp" %>
</body>
<script type="module" src="./js/actualizarHojaEnvio.js"></script>
<script>
$(document).ready(function() {
    $('#busqueda').select2({
    	theme: "bootstrap-5"
    });
});
</script>
</html>