<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<title>Actualizar Pedidos</title>
	
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
	<c:if test="${ nomUsuario == null || nomUsuario.getIdTipoUsuario() == 2 }">
		<c:redirect url="indexUser.jsp"></c:redirect>
	</c:if>
 	
 	<%@ include file="snippets/navAdmin.jsp" %>
    	
    <main class="main">
        <div class="box">
            <div class="header-info">
               	<h2>Actualizar pedidos</h2>

                 <form class="form-search" method="POST">
                 	<select class="form-select" name="cboEstado" id="cboPosition">
                    	<option value="all">Todos</option>
                        <c:forEach var="Estado" items="${ listaEstados }">
                        	<option value="${ Estado.getCodEstadoPedido() }" ${ Estado.getCodEstadoPedido() == estadoB ? 'selected' : '' }>
                                ${ Estado.getNomTipoEstado() }
                           	</option>
                      	</c:forEach>     
                  	</select>
                           
                  	<input class="form-control" type="text" id="search" name="search" placeholder="DNI cliente..." value="${ busqueda == null ? '' : busqueda }" />
               		<input class="form-control" type="date" id="start-date" name="startDate" placeholder="Fecha de Inicio" min="2000-01-01" max="2030-12-31" value="${ fechaInicio == null ? '' : fechaInicio }" />
                	<input class="form-control" type="date" id="end-date" name="endDate" placeholder="Fecha de Fin" min="2000-01-01" max="2030-12-31" value="${ fechaFinal == null ? '' : fechaFinal }" />
                 	<input class="form-control bg-primary text-white" type="submit" value="Aplicar Búsqueda" />
             	</form>
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
                    	<c:forEach items="${listaPedido}" var="Pedido">
                        	<div class="d-row">
                            	<div class="data">
                                	<input type="hidden" name="id" value=${ Pedido.getCodPedido()}>
                                	<input type="hidden" name="codEstado" value=${ Pedido.getCodEstadoPedido()}>
                                  	<input type="hidden" name="codMetodoRecojo" value=${ Pedido.getCodMetodoRecojo()}>

                                  	<span>${ Pedido.getCodPedido() }</span>
                                   	<span>${ Pedido.getFechaPedido() }</span>
                                  	<span>${ Pedido.getEstados().getNomTipoEstado() }</span>    
                                  	<span>${ Pedido.getMetodo().getNomTipoRecojo() }</span>
                                 	<span>${ Pedido.getPrecTotPedido() }</span>
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
	                        	<a class="link-active" href='PedidosController?type=listarPedidos&p=${ pag - 1 }'>Anterior</a>
	                      	</c:if>
	                                      
	                      	<c:forEach var="i" begin="${ comienza }" end="${ termina }">
	          					<a ${ pag == i ? "class='link-desactive'" : "class='link-active' href='PedidosController?type=listarPedidos&p=" += i += "'"  }>${ i }</a>	
	        				</c:forEach>
	
	                       	<c:if test="${ pag < totalDePaginacion }">
	                        	<a class="link-active" href='PedidosController?type=listarPedidos&p=${ pag + 1 }'>Siguiente</a>
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
<script type="module" src="./js/actualizarPedidos.js"></script>
</html>