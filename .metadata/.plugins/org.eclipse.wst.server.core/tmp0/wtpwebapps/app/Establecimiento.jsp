<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<title>Establecimiento</title>
	
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
			<h1>Actualizar Establecimiento</h1>
			
			<form action="Establecimiento" method="post">
				<input type="hidden" name="type" value="updateInfoObject">

				<c:forEach var="listarEstablecimiento" items="${ ListarEstablecimiento }">
					<div>
						<label class="mt-3 h4" for="txtSobreNosotros">Sobre Nosotros</label>
						
						<div>
							<textarea class="form-control" name="txtSobreNosotros" id="txtSobreNosotros" style="height: 200px;">${ listarEstablecimiento.getSobreNosotros() }</textarea>
							<div id="establishment-invalid" class="text-start invalid-feedback">El Texto no puede estar vacío y solo se acepta un máximo de 500 caracteres.</div>
						</div>
					</div>
				</c:forEach>

				<input type="submit" id="btn-update-establishment" class="btn btn-primary my-2" value="Enviar Datos">
			</form>
		</div>
	</main>
	
	<%@ include file="snippets/modal-template.jsp"%>
</body>
<script type="module" src="./js/Establecimiento.js"></script>
</html>