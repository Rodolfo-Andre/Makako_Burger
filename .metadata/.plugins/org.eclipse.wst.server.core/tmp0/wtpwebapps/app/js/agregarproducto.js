import useFetch from "./fetch.js";
import { showModal } from "./modal.js";

const $d = document;

$d.addEventListener("DOMContentLoaded", () => {
	heandOnClick();
});

const heandOnClick = () => {
	//boton de agregar
	const $listBtnAñadirProdCarrito = Array.from($d.querySelectorAll(".btnCarrito"));

	$d.addEventListener("click", (e) => {
		if ($listBtnAñadirProdCarrito.includes(e.target)) {
			let props = {
				url: "PedidosController?" + new URLSearchParams({ type: "obtenerUsuario" }),
				success: async (json) => {
					let usuario = await json;

					if (!usuario) {
						let contentModal = {
							header: `<h4 class="modal-title">Usuario no autenticado</h4>
									<button class="border-0 bg-transparent fs-3" data-bs-dismiss="modal" aria-label="Close"><i class="bi bi-x-circle-fill"></i></button>`,
							body: `<p>Debes iniciar sessión para poder agregar productos al carrito.</p>`
						}

						showModal(contentModal);
						return;
					}

					if (usuario.idTipoUsuario == 1) {
						let contentModal = {
							header: `<h4 class="modal-title">No tienes los permisos para realizar una compra.</h4>
						       		<button class="border-0 bg-transparent fs-3" data-bs-dismiss="modal" aria-label="Close"><i class="bi bi-x-circle-fill"></i></button>`,
							body: `<p>Debes iniciar sesión con una cuenta de tipo cliente.</p>`,
						}

						showModal(contentModal);
					} else {
						//buscamos todos los botones y le mandamos la misma funcion
						let $btnAñadir = e.target,
							$inputIdProd = $btnAñadir.parentNode.parentNode.querySelector("input[name='IdProducto']");
					
						if (!$inputIdProd) {
							$inputIdProd = $btnAñadir.parentNode.parentNode.querySelector("input[name='IdPromo']");
						}

						let IdProducto = $inputIdProd.value;

						let params = {
							type: "agregarproducto",
						}

						if ($inputIdProd.name == "IdProducto") {
							params["object"] = "Producto";
							params["IdProducto"] = IdProducto;
						} else {
							params["object"] = "Promo";
							params["IdPromo"] = IdProducto;
						}

						let props01 = {
							url: "PedidosController?" + new URLSearchParams(params),
							success: async (json) => {
								let { agregado } = await json;

								if (agregado) {
									let contentModal = {
										header: `<h4 class="modal-title">Producto Agregado</h4>
									        	<button class="border-0 bg-transparent fs-3" data-bs-dismiss="modal" aria-label="Close"><i class="bi bi-x-circle-fill"></i></button>`,
										body: `<p>El producto fue agregado correctamente</p>`,
									}

									showModal(contentModal);
								} else {
									if (!$inputIdProd.classList.contains("is-invalid")) $inputIdProd.classList.add("is-invalid");
									$inputIdProd.textContent = "Error al agregar producto";
								}
							},
							options: {
								method: "POST",
							}
						}

						useFetch(props01);
					}
				},
				options: {
					method: "POST",
				}
			}

			useFetch(props);
		}
	});
}