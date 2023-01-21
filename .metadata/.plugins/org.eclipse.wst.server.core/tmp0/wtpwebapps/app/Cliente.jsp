<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<title>Registrar Cliente</title>
	
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
	
	<!--BOOTSTRAPVALIDATOR - CSS-->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css" integrity="sha512-Mqfoc3Z3HrXDAkb9KWQklaedI9QNM01mqRaruK/LWtT3JU6BuxnbHg0MTqowzr2P8/Xdd0ITR0po3A4R5T0h2w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
	<div class="d-flex min-vh-100">
		<div class="d-none d-md-flex w-50 align-items-center justify-content-center shadow" style="background-color: #FFF6EA;">
			<img alt="" src="./img/makako_burger.png">
		</div>

		<div class="d-flex flex-grow-1 mx-3">
			<div class="box-data register shadow">
				<h2 class="text-center">Registrarse</h2>
				
				<c:if test="${ DniMensaje != null }">
					<p class="text-danger">-${ DniMensaje }</p>
				</c:if>
				
				<c:if test="${ ValidarCorreo != null }">
					<p class="text-danger">-${ ValidarCorreo }</p>
				</c:if>
		
				<form action="ClienteController" class="d-flex flex-column gap-4" method="post" id="form">
					<input type="hidden" name="type" value="RegistrarCliente">
	
					<div class="row">
						<label class="col-sm-3 col-form-label">Nombre:</label>
						
						<div class="col-sm-9">
							<input class="form-control" type="text" name="txtName" placeholder="Ingrese su nombre...">
						</div>
					</div>
	
					<div class="row">
						<label class="col-sm-3 col-form-label">Apellido:</label>
	
						<div class="col-sm-9">
							<input class="form-control" type="text" name="txtApellido" placeholder="Ingrese su apellido...">
						</div>
					</div>
	
					<div class="row">
						<label class="col-sm-3 col-form-label">DNI:</label>
	
						<div class="col-sm-9">
							<input class="form-control" type="text" name="TxtDni" placeholder="88888888">
						</div>
					</div>
	
					<div class="row">
						<label class="col-sm-3 col-form-label">Correo:</label>
	
						<div class="col-sm-9">
							<input class="form-control" type="text" name="txtCorreo" placeholder="user@gmail.com">
						</div>
					</div>
	
					<div class="row">
						<label class="col-sm-3 col-form-label">Contraseña:</label>
	
						<div class="col-sm-9">
							<input class="form-control" type="password" name="txtContrasena" placeholder="********">
						</div>
					</div>
	
					<input type="submit" class="btn btn-primary" name="nameBtn" value="Registrar">
				</form>
	
				<br>
					
				<div class="d-flex flex-wrap justify-content-between">
					<label>¿Ya tienes cuenta?</label>
					
					<div>
						<a href="login.jsp">Inicia Session</a>
					</div>
				</div>
			</div>
		</div>	
	</div>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js" integrity="sha512-STof4xm1wgkfm7heWqFJVn58Hm3EtS31XFaagaa8VMReCXAkQnJZ+jEy8PCC/iT18dFy95WcExNHFTqLyp72eQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js" integrity="sha512-Vp2UimVVK8kNOjXqqj/B0Fyo96SDPj9OCSm1vmYSrLYF3mwIOBXh/yRZDVKo8NemQn1GUjjK0vFJuCSCkYai/A==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#form').bootstrapValidator({
			feedbackIcons : {
				valid : 'd-none',
				invalid : 'bi bi-x-circle-fill text-danger align-middle',
				validating : 'd-none'
			},
			fields : {
				txtName : {
					validators : {
						notEmpty : {
							message : "El campo es obligatorio"
						},
						stringLength : {
							min : 5,
							max : 50,
							message : "El campo requiere min 5 y max 50 caracteres"
						}
					}
				},
				txtApellido : {
					validators : {
						notEmpty : {
							message : "El campo es obligatorio"
						},
						stringLength : {
							min : 5,
							max : 50,
							message : "El campo requiere min 5 y max 50 caracteres"
						}
					}
				},								
				TxtDni : {
					validators : {
						notEmpty : {
							message : "El campo es obligatorio"
						},
						regexp : {
							regexp : /^[0-9]{8}$/,
							message : "Formato de Dni incorrecto"
						}
					}
				},								
				txtCorreo : {
					validators : {
						notEmpty : {
							message : "El campo es obligatorio"
						},
						regexp : {
							regexp : /^[A-Za-z0-9._]+@[a-z]{4,8}(\.[a-z]{2,4}){1,2}$/,
							message : "El formato de correo es incorrecto"
						}
					}
				},								
				txtContrasena : {
					validators : {
						notEmpty : {
							message : "El campo es obligatorio",
						},
						regexp: {
							regexp: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])([A-Za-z\d$@$!%*?&]|[^ ]){7,25}$/,
							message: "Introduce una contraseña segura. Mínimo 7 y máximo 25 caracteres. Al menos 1 mayúscula, 1 minúscula, 1 dígito y 1 caracter especial. No se aceptan espacios"
						}
					}
				}								
			}
		});

		$('#nameBtn').click(function() {
			$('#form').bootstrapValidator('validate');
		});
	});
</script>
</html>