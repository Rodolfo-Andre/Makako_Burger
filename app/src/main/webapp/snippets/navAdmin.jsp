<div class="d-flex flex-column shadow" style="width: 280px; background-color: #FAE6C5;">
	<a href="indexUser.jsp" style="border-bottom: 1px solid #FAE6C5;">
		<img src="./img/cartel.jpg" alt="" class="img-fluid">
	</a>

	<ul class="nav flex-column mb-auto">
		<li>
			<a href="indexAdmin.jsp" class="btn-home"> 
				<i class="bi bi-house-door-fill fs-4"></i>
                 
                 <span class="fs-5 fw-semibold">Inicio</span>
			</a>
		</li>

		<li class="nav-item">
			<a href="#" class="nav-link link-dark" data-bs-toggle="collapse" aria-expanded="true" aria-controls="menu" data-bs-target="#menu">
				<i class="bi bi-journals fs-4"></i>
	           	
	           	<span class="fs-5 fw-semibold ">Pedidos</span>
			</a> 
		
			<!--desplegable Comandas-->
			<div class="collapse" id="menu">
				<ul class="ms-3 pb-1 list-unstyled">
					<li class="nav-item">
						<a href="PedidosController?type=listarPedidos&p=1" class="nav-link link-dark"> 
							<i class="bi bi-receipt-cutoff fs-4"></i>
                           	
                           	<span class="fw-semibold">Actualizar pedidos</span>   	
                       	</a>
                   	</li>

					<li class="nav-item">
						<a href="HojaEnvioController?p=1" class="nav-link link-dark"> 
							<i class="bi bi-search fs-4"></i>
                           	
                           	<span class="fw-semibold">Actualizar Hoja de envío</span>
						</a>
					</li>
				</ul>
			</div>
		</li>

		<li>
			<a href="CajaController?p=1" class="nav-link link-dark">
				<i class="bi bi-cash-coin fs-4"></i>
               	
               	<span class="fs-5 fw-semibold">Caja Registradora</span>
			</a>
		</li>

		<li>
			<a href="#" class=" nav-link link-dark" data-bs-toggle="collapse" aria-expanded="true" aria-controls="menuConfiguracion" data-bs-target="#menuConfiguracion"> 
				<i class="bi bi-wrench-adjustable-circle fs-4"></i>
                
               	<span class="fs-5 fw-semibold">Configuración</span>
			</a>
			 
			<!--desplegable Configuración-->
			<div class="collapse" id="menuConfiguracion">
				<ul class="ms-3 pb-1 list-unstyled">
					<li class="nav-item">
						<a href="EmpleadosController?p=1" class="nav-link link-dark">
							<i class="bi bi-people-fill fs-4"></i>
                            
                            <span class="fw-semibold">Configurar Empleados</span>
						</a>
					</li>

					<li class="nav-item ">
						<a href="ProductosController?pProductos=1" class="nav-link link-dark"> 
							<i class="bi bi-egg-fried fs-4"></i>
                           	
                           	<span class="fw-semibold">Configurar Productos</span>
						</a>
					</li>

					<li class="nav-item">
						<a href="PromocionesController?p=1" class="nav-link link-dark"> 
							<i class="bi bi-cup-hot-fill fs-4"></i>
                    		
                    		<span class="fw-semibold">Configurar Promociones</span>
						</a>
					</li>

					<li class="nav-item">
						<a href="ZonaDeRepartoController?p=1" class="nav-link link-dark">
							<i class="bi bi-geo-alt-fill fs-4"></i>
							
							<span class="fw-semibold">Configurar zona de repartición</span>
						</a>
					</li>
					
					<li class="nav-item">
						<a href="Establecimiento?type=listarinfo" class="nav-link link-dark">
							<i class="bi bi-bank fs-4"></i>
							
							<span class="fw-semibold">Configurar establecimiento</span>
						</a>
					</li>
				</ul>
			</div>
		</li>
		
		<li class="nav-item">
			<a href="#" class="nav-link link-dark" data-bs-toggle="collapse" aria-expanded="true" aria-controls="reporte" data-bs-target="#reporte"> 
				<i class="bi bi-journals fs-4"></i> 
               	
               	<span class="fs-5 fw-semibold">Reportes</span>
			</a>
			
			<div class="collapse" id="reporte">
				<ul class="ms-3 pb-1 list-unstyled">
					<li class="nav-item">
						<a href="ReclamosController?p=1" class="nav-link link-dark"> 
							<i class="bi bi-book fs-4"></i>
							
							<span class="fw-semibold">Reclamos</span>
						</a>
					</li>
				</ul>
			</div>
		</li>
	</ul>

	<!-- USUARIO IMAGEN-->
	<div class="shadow-lg p-1 " style="border-top: 2px solid #FAE6C5;">
		<a href="#" class="d-flex align-items-center justify-content-center p-2 link-dark text-decoration-none dropdown-toggle" id="dropdownUser3" data-bs-toggle="dropdown" aria-expanded="false">
			<img src="./img/default_user.png" alt="photo" style="width: 40px; height: 40px; border-radius: 50%;"> 
			
			<span class="fs-6 fw-semibold mx-2">${ empleado.getNom_Empleado() }</span>
		</a>

		<ul class="dropdown-menu dropdown-menu-end">
			<li>
				<a href="indexUser.jsp" class="dropdown-item fw-semibold py-2">Vista Cliente</a>
			</li>
			
			<li>
				<a href="login?type=logout" class="dropdown-item fw-semibold py-2">Cerrar Sesión</a>
			</li>
		</ul>
	</div>
</div>
