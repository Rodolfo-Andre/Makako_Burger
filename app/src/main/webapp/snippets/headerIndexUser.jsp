<header class="text-center shadow-lg mb-4" style="background-color: #FABE1F;">
	<video src="./img/titulo.mp4" style="width: 100%; max-width: 500px;" autoplay loop muted="muted"></video>
		
	<nav class="navbar navbar-expand-xl" style="background-color: #FFF6EA;">
		<div class="container-fluid">
			<a class="navbar-brand" style="color: #FFF6EA;" href="indexUser.jsp">
				<img src="./img/logo_fondo_transparente.png" width="90" alt="logo">
			</a>

			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" style="color: #330E00;" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<i class="fa-solid fa-bars"></i>
			</button>
				
			<div class="collapse navbar-collapse justify-content-end" id="navbarNav">
				<ul class="navbar-nav gap-2 align-items-center" style="color: #FFF6EA;">
					<li class="nav-item">
						<a class="nav-link" style="color: #330E00; font-size: 20px;" aria-current="page" href="indexUser.jsp">
							<i class="fa-solid fa-house-chimney d-flex justify-content-center"></i>
							Inicio
						</a>
					</li>

					<li class="nav-item">
						<a class="nav-link" style="color: #330E00; font-size: 20px;" aria-current="page" href="menuUsuario.jsp">
							<i class="fa-solid fa-burger d-flex justify-content-center"></i>
							Menú
						</a>
					</li>

					<li class="nav-item">
						<a class="nav-link" style="color: #330E00; font-size: 20px;" aria-current="page" href="PedidosController?type=ListarPromocion">
							<i class="fa-solid fa-sack-dollar d-flex justify-content-center"></i>
							Promociones
						</a>
					</li>

					<li class="nav-item">
						<a class="nav-link" style="color: #330E00; font-size: 20px;" aria-current="page" href="Zona_de_reparto.jsp"> 
							<i class="fa-solid fa-map-location-dot d-flex justify-content-center"></i>
							Zona de Reparto
						</a>
					</li>

					<li class="nav-item">
						<a class="nav-link" style="color: #330E00; font-size: 20px;" aria-current="page" href="SobreNosotrosController?type=listarinfo"> 
							<i class="fa-solid fa-people-roof d-flex justify-content-center"></i>
							Nosotros
						</a>
					</li>

					<li class="nav-item d-flex align-self-center btn-item">
						<a class="nav-link p-3" style="color: #330E00; font-size: 20px;" aria-current="page" href="PedidosController?type=carritoCompra"> 
							<i class="fa-solid fa-shopping-cart justify-content-center pe-2"></i> 
							Tienda Online
						</a>
					</li>

					<c:choose>
						<c:when test="${ nomUsuario != null }">
							<div class="nav-item dropdown d-flex align-self-center btn-item" style="font-size: 20px;">
								<a href="#" class="d-flex align-items-center p-2 link-dark text-decoration-none dropdown-toggle user-photo" id="dropdownUser3" data-bs-toggle="dropdown" aria-expanded="false"> 
									<img src="./img/default_user.png" alt="photo" style="width: 40px; height: 40px; border-radius: 50%;">
									<span class="m-2">${ empleado != null ? empleado.getNom_Empleado() : cliente.getNomCliente() }</span>
								</a>

								<ul class="dropdown-menu dropdown-menu-end p-0" style="color: #330E00; font-size: 20px; background-color: #FABE1F; position: absolute;">
									<c:choose>
										<c:when test="${ nomUsuario.getIdTipoUsuario() == 1 }">
											<li>
												<a href="indexAdmin.jsp" class="dropdown-item fw-bold py-2">Configuración</a>
											</li>
										</c:when>
										
										<c:otherwise>
											<li>
												<a href="CajaController?type=listarCDPCli" class="dropdown-item fw-bold py-2">Ver mis CDP</a>
											</li>
										</c:otherwise>
									</c:choose>

									<li>
										<hr class="dropdown-divider m-0">
									</li>
										
									<li>
										<a href="login?type=logout" class="dropdown-item fw-bold py-2">Cerrar Sesión</a>
									</li>
								</ul>
							</div>
						</c:when>
						
						<c:otherwise>
							<li class="nav-item d-flex align-self-center btn-item">
								<a class="nav-link p-3" style="color: #330E00; font-size: 20px;" aria-current="page" href="login.jsp">
									<i class="fa-solid fa-user justify-content-center pe-2"></i>
									Iniciar Sesión
								</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>
</header>