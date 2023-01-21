<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<title>Inicio de Sesión</title>
	
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
	<link rel="stylesheet" type="text/css" href="./css/sign_in_up_style.css">
	
	<!--SCRIPTS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
</head>
<body>
	<div class="d-flex vh-100">
		<div class="d-none d-md-flex w-50 align-items-center justify-content-center shadow" style="background-color: #FFF6EA;">
			<img alt="" src="./img/makako_burger.png">
		</div>

		<div class="d-flex flex-grow-1 mx-3">
			<div class="box-data login shadow">
				<h2 class="text-center">Inicio de Sesión</h2>
	
				<form class="login-form needs-validation" action="login" method="POST">
					<input class="" type="hidden" name="type" value="login">
	
					<div>
						${invalid ? '<p class="login-error">Correo o contraseña incorrecta</p>' : '' }
	
						<input class="form-control ${invalid ? 'is-invalid': ''}" type="text" id="email" name="email" autocomplete="email" placeholder="Email*">
						<div id="email-invalid" class="text-start invalid-feedback"></div>
					</div>
	
					<div>
						<input class="form-control ${invalid ? 'is-invalid': ''}" type="password" id="password" name="password" autocomplete="current-password" placeholder="Password*">
						<div id="password-invalid" class="text-start invalid-feedback"></div>
					</div>
	
					<a class="recover-password">¿Olvidaste tu contraseña?</a> 
					
					<input class="btn btn-primary" id="validate-login" type="submit" value="INGRESAR AL SISTEMA">
				</form>
				
				<br>
				
				<div class="d-flex flex-wrap justify-content-between">
					<label>¿No tienes cuenta?</label>
					
					<div>
						<a href="Cliente.jsp">Registrarse</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Modal Recuperar Contraseña -->
	<template id="modal-template">
		<div class="modal fade" id="recover-password" tabindex="-1" aria-labelledby="recover-password-label" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header"></div>

					<div class="modal-body"></div>

					<div class="modal-footer"></div>
				</div>
			</div>
		</div>
	</template>
</body>
<script type="module" src="./js/login.js"></script>
</html>