<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" type="image/png" href="./img/icono.jpg" sizes="32x32"/>
	<title>Makako Burger</title>
	
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
		<div class="container-fluid">
			<div id="myCarousel" class="carousel slide" >
				<div class="carousel-indicators">
					<button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
					<button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
					<button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
				</div>
				
				<div class="carousel-inner">
					<div class="carousel-item active h-100">
						<img src="./img/banner_1.jpg">
					</div>
					
					<div class="carousel-item h-100">
						<video src="./img/banner_2.mp4" autoplay loop muted></video>
					</div>
					
					<div class="carousel-item h-100">
						<video src="./img/banner_3.mp4" autoplay loop muted></video>
					</div>
				</div>
			</div>

			<div class="border m-4 mx-auto rounded-3 d-flex flex-wrap border-1" style="max-width: 600px">
				<div class="p-3 border-end border-1 d-flex justify-content-center flex-grow-1" style="background-color: #FFF6EA;">
					<img src="./img/makako_burger.png" class="img-fluid" alt="Makako Burger" style="width: 100%; max-width: 200px;">
				</div>

				<div class="p-3 d-flex flex-column justify-content-center flex-grow-1">
					<div>
						<h2 class="text-center makako-font text-uppercase">Makako Social</h2>

						<div class="d-flex flex-column gap-4">
							<div class="text-start ">
								<a href="https://www.facebook.com/profile.php?id=100063526752640" target="_blank" class="text-decoration-none text-white bg-primary p-2 rounded-2 border-0" style="letter-spacing: 1.5px">
									<i class="fa-brands fa-facebook p-1"></i>
									Facebook
								</a>						
							</div>

							<div class="text-center">
								<a href="https://www.instagram.com/makakoburgercafe/" target="_blank" class="text-decoration-none text-white p-2 rounded-2 border-0" style="letter-spacing: 1.5px; background: linear-gradient(25deg, #405de6, #5851db, #833ab4, #c13584, #e1306c, #fd1d1d);">
									<i class="fa-brands fa-instagram"></i> 
									Instagram
								</a>
							</div>

							<div class="text-end">
								<a href="https://api.whatsapp.com/send?phone=51966313252" target="_blank" class="text-decoration-none text-white p-2 rounded-2 border-0" style="letter-spacing: 1.5px; background-color: #4DC95B;">
									<i class="fa-brands fa-whatsapp p-1"></i>
									WhatsApp
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>
	
	<%@ include file="snippets/footerIndexUser.jsp"%>
</body>
</html>