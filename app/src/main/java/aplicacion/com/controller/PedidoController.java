package aplicacion.com.controller;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import aplicacion.com.dao.DAOFactory;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.*;
import aplicacion.com.utils.Fecha;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Servlet implementation class PromocionController
 */
@WebServlet("/PedidosController")
public class PedidoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PedidoController() {
		super();
	}

	private DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
	private ProductoDao productoDao = daoFactory.getProducto();
	private EstadosPedidoDAO estadoDAO = daoFactory.getEstadoPedido();
	private PromocionDAO promocioDao = daoFactory.getPromocion();
	private PedidoDAO pedidoDAO = daoFactory.getPedido();
	private CatProdDao catProdDao = daoFactory.getCatProd();
	private Fecha fechas = new Fecha();

	private PrintWriter pw;
	private Gson gson = new GsonBuilder().setDateFormat(Fecha.formato.toPattern()).create();

	// Página
	private int pag = 1;
	private int nroDeRegistroXPag = 5;
	private int maxDePaginacion = 5;
	private int totalDePaginacion = 0;
	private int comienza = 0;
	private int termina = 0;
	private int cantTotalDeRegistro = 0;

	// Variables de búsqueda
	private String busqueda = "";
	
	// Filtros de búsqueda
	private String estadoPedido = "";
	private String fechaInicio = "";
	private String fechaFinal = "";

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		pw = response.getWriter();
		String tipo = request.getParameter("type");

		if (tipo != null) {
			switch (tipo) {
				case "ListarProducto": {
					obtenerProductosPorCategoria(request, response);
					break;
				}
				case "ListarPromocion": {
					obtenerPromocion(request, response);
					break;
				}
				case "agregarproducto": {
					agregarCarrito(request, response);
					break;
				}
				case "eliminarproducto": {
					eliminaragregar(request, response);
					break;
				}
				case "listarPedidos": {
					try {
						listarPedidos(request, response);
					} catch (ServletException | IOException | ParseException e) {
						e.printStackTrace();
					}
					break;
				}
				case "getPedido": {
					getPedido(request, response);
					break;
				}
				case "getDetallePedido": {
					getDetallePedido(request, response);
					break;
				}
				case "actualizarEstadoPedido": {
					actualizarEstadoPedido(request, response);
					break;
				}
				case "carritoCompra": {
					carritoCompra(request, response);
					break;
				}
				case "actualizarCarrito": {
					actualizarCarrito(request, response);
					break;
				}
				case "obtenerUsuario": {
					obtenerUsuario(request, response);
					break;
				}
			}
		}
	}

	private void actualizarCarrito(HttpServletRequest request, HttpServletResponse response) {
		//genera un objeto tipo json 
		JsonElement jsonElement = JsonParser.parseString(request.getParameter("carrito"));
		
		//gson.fromJson desearealizar un json a un objeto de java
		ArrayList<DetallePedido> listaDetallePedido = gson.fromJson(jsonElement, new TypeToken<ArrayList<DetallePedido>>(){}.getType());
				
		request.getSession().setAttribute("DetallePedido", listaDetallePedido);
				
		pw.print(new JsonObject());
	}

	private void getPedido(HttpServletRequest request, HttpServletResponse response) {
		int idPedido = Integer.parseInt(request.getParameter("id"));
		Map<String, Object> filtros = new HashMap<>();
		Pedido ped = pedidoDAO.findAll(filtros).stream().filter(p -> p.getCodPedido() == idPedido).findFirst()
				.orElse(null);
		
		if (ped != null) {
			String pedidoJsonString = gson.toJson(ped);
			pw.print(pedidoJsonString);
		}
	}

	private void getDetallePedido(HttpServletRequest request, HttpServletResponse response) {
		int idPedido = Integer.parseInt(request.getParameter("id"));
		ArrayList<DetallePedido> detalle = pedidoDAO.findAllDetallePedidoByCodPedido(idPedido);
		String listDetalleJSON = gson.toJson(detalle);
		
		System.out.print(listDetalleJSON);
		
		pw.print(listDetalleJSON);
	}

	private void actualizarEstadoPedido(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int idPedido = Integer.parseInt(request.getParameter("id"));
		pedidoDAO.actualizarPedido(2, idPedido);
		response.sendRedirect("PedidosController?type=listarPedidos&p=" + pag);
	}

	private void listarPedidos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		/* ------------------ Variable de Búsqueda ------------------ */
		Map<String, Object> filtros = new HashMap<>();

		String validaBusqueda = request.getParameter("search");
		String validaEstado = request.getParameter("cboEstado");
		String validaFechaInicio = request.getParameter("startDate");
		String validaFechaFinal = request.getParameter("endDate");

		busqueda = validaBusqueda != null ? validaBusqueda : busqueda;
		if (!busqueda.isBlank()) filtros.put("busqueda", busqueda);

		if (validaEstado != null) {
			if (validaEstado.equals("all")) {
				estadoPedido = "";
				filtros.put("idEstado", estadoPedido);
			} else {
				estadoPedido = validaEstado;
				filtros.put("idEstado", estadoPedido);
			}
		}

		fechaInicio = validaFechaInicio != null ? validaFechaInicio : fechaInicio;
		if (!fechaInicio.isBlank()) {
			Date fechaInicioD = Fecha.formato.parse(fechaInicio);
			filtros.put("fechaInicio", fechaInicioD);
		}

		fechaFinal = validaFechaFinal != null ? validaFechaFinal : fechaFinal;
		if (!fechaFinal.isBlank()) {
			Date fechaFinalD = Fecha.formato.parse(fechaFinal);
			filtros.put("fechaFinal", fechaFinalD);
		}

		String validaPag = request.getParameter("p");

		if (validaBusqueda != null || validaEstado != null || validaFechaInicio != null || validaFechaFinal != null) {
			validaPag = "1";
		}

		/* ------------------ Proceso de Paginación ------------------ */
		int pagPeticion = 0;
		double mitadMaximoPaginacion = Math.round(maxDePaginacion / 2.0);
		ArrayList<EstadosPedido> listEstados = estadoDAO.findAll();
		ArrayList<Pedido> listPedidos = pedidoDAO.findAll(filtros);

		cantTotalDeRegistro = listPedidos.size();
		totalDePaginacion = cantTotalDeRegistro % nroDeRegistroXPag == 0 ? cantTotalDeRegistro / nroDeRegistroXPag
				: (cantTotalDeRegistro / nroDeRegistroXPag) + 1;

		if (validaPag != null && validaPag.matches("^[0-9]+$")
				&& (pagPeticion = Integer.parseInt(validaPag)) <= totalDePaginacion && pagPeticion > 0) {
			pag = pagPeticion;
		}

		if (cantTotalDeRegistro > 0) {
			int desde = (nroDeRegistroXPag * pag) - nroDeRegistroXPag;
			int hasta = (nroDeRegistroXPag * pag) > cantTotalDeRegistro ? cantTotalDeRegistro : nroDeRegistroXPag * pag;

			List<Pedido> listaPedidoTemporal = listPedidos.subList(desde, hasta);

			if (pagPeticion - mitadMaximoPaginacion <= 0) {
				comienza = 1;
			} else {
				comienza = (int) (pagPeticion - mitadMaximoPaginacion);
			}

			if (comienza + maxDePaginacion - 1 > totalDePaginacion) {
				termina = totalDePaginacion;
				comienza = termina - maxDePaginacion + 1 <= 0 ? 1 : termina - maxDePaginacion + 1;
			} else {
				termina = comienza + maxDePaginacion - 1;
			}

			request.setAttribute("comienza", comienza);
			request.setAttribute("termina", termina);
			request.setAttribute("listaPedido", listaPedidoTemporal);
		}

		request.setAttribute("pag", pag);
		request.setAttribute("totalDePaginacion", totalDePaginacion);
		request.setAttribute("totalRegistro", listPedidos.size());
		request.setAttribute("listaEstados", listEstados);
		request.setAttribute("busqueda", busqueda);
		request.setAttribute("estadoB", estadoPedido);
		request.setAttribute("fechaInicio", fechaInicio);
		request.setAttribute("fechaFinal", fechaFinal);
		request.getRequestDispatcher("actualizarPedido.jsp").forward(request, response);
	}

	private void eliminaragregar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int IdProducto = Integer.parseInt(request.getParameter("IdProducto"));
		productoDao.deleteProducto(IdProducto);
		response.sendRedirect("ProductosController?type=eliminarproducto");
	}

	private void carritoCompra(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String peticion = request.getParameter("js");
		
		@SuppressWarnings("unchecked")
		ArrayList<DetallePedido> DetaPedido = (ArrayList<DetallePedido>) request.getSession().getAttribute("DetallePedido");
		
		if (peticion == null) {
			request.setAttribute("listProducto", DetaPedido);
			request.getRequestDispatcher("Carrito.jsp").forward(request, response);			
		}
		
		String DetaPedidoString = gson.toJson(DetaPedido);
	
		pw.print(DetaPedidoString);
	}	

	@SuppressWarnings("unchecked")
	private void agregarCarrito(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String object = request.getParameter("object");

		if (object.equals("Producto")) {
			int IdProducto = Integer.parseInt(request.getParameter("IdProducto"));
			// llamamos las entidades de producto
			Map<String, Object> filtrosProd = new HashMap<>();
			ArrayList<Producto> prodsList = productoDao.listProd(filtrosProd);
			Producto producto = prodsList.stream().filter(p -> p.getCodProd() == IdProducto).findFirst().orElse(null);
			ArrayList<DetallePedido> DetaPedido = (ArrayList<DetallePedido>) request.getSession().getAttribute("DetallePedido");
			
			// hacemos una interracion con el arreglo para ver el producto existente
			// si el producto no existe retorna nulo
			DetallePedido deta = DetaPedido.stream().filter(dp -> dp.getCodProd() == IdProducto).findFirst()
					.orElse(null);

			Map<String, Object> filtros = new HashMap<>();
			double precioProductoU = productoDao.listProd(filtros).stream().filter(p -> p.getCodProd() == IdProducto)
					.findFirst().orElse(null).getPrecioPro();

			JsonObject jsonobject = new JsonObject();
	
			// Validamos si el momento de que se haya encontrado un producto en el carrito
			if (deta != null) {
				deta.setProduct(producto);
				deta.setCodProd(IdProducto);
				deta.setCant(deta.getCant() + 1);
				double total = precioProductoU * deta.getCant();
				deta.setPrecioPedidoTot(total);
			} else {
				deta = new DetallePedido();
				deta.setProduct(producto);
				deta.setCodPromo(0);
				deta.setCodProd(IdProducto);
				deta.setCant(deta.getCant() + 1);
				deta.setPrecioPedidoTot(precioProductoU * deta.getCant());
				DetaPedido.add(deta);
			}
			
			jsonobject.addProperty("agregado", true);
			
			// se envia como respuesta al js
			pw.print(jsonobject);
		} else {
			int idPromo = Integer.parseInt(request.getParameter("IdPromo"));

			// llamamos las entidades de promo
			Map<String, Object> filtrosPromo = new HashMap<>();
			ArrayList<Promocion> Promo = promocioDao.findAllPromocion(filtrosPromo);
			Promocion promos = Promo.stream().filter(p -> p.getCodPromo() == idPromo).findFirst().orElse(null);

			ArrayList<DetallePedido> DetaPedido = (ArrayList<DetallePedido>) request.getSession().getAttribute("DetallePedido");

			// hacemos una interracion con el arreglo para ver el producto existente
			// si el producto no existe retorna nulo
			DetallePedido deta = DetaPedido.stream().filter(dp -> dp.getCodPromo() == idPromo).findFirst().orElse(null);

			Map<String, Object> filtros = new HashMap<>();
			double precioPromoU = promocioDao.findAllPromocion(filtros).stream().filter(p -> p.getCodPromo() == idPromo)
					.findFirst().orElse(null).getPrecioPromo();
			
			JsonObject jsonobject = new JsonObject();
			
			// Validamos si el momento de que se haya encontrado un producto en el carrito
			if (deta != null) {
				deta.setPromo(promos);
				deta.setCodProd(idPromo);
				deta.setCant(deta.getCant() + 1);
				double total = precioPromoU * deta.getCant();
				deta.setPrecioPedidoTot(total);
			} else {
				deta = new DetallePedido();
				deta.setPromo(promos);
				deta.setCodProd(0);
				deta.setCodPromo(idPromo);
				deta.setCant(deta.getCant() + 1);
				deta.setPrecioPedidoTot(precioPromoU * deta.getCant());
				DetaPedido.add(deta);			
			}
			
			jsonobject.addProperty("agregado", true);
			
			// se envia como respuesta al js
			pw.print(jsonobject);
		}
	}

	private void obtenerPromocion(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DateTimeFormatter FechaActual = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String fechaActual = String.valueOf(FechaActual.format(LocalDateTime.now()));
		Map<String, Object> filtrosPromo = new HashMap<>();
		
		filtrosPromo.put("fechaInicio", fechas.StringADatetime(fechaActual));
		
		ArrayList<DetallePromocion> listDetallePromos = promocioDao.findAllDetallePromoByidPromo(0);
		ArrayList<Promocion> nombre = promocioDao.findAllPromocion(filtrosPromo);
		
		request.setAttribute("listPromo", nombre);
		request.setAttribute("listDetallePromo", listDetallePromos);
		request.getRequestDispatcher("PromocionUser.jsp").forward(request, response);
	}

	private void obtenerProductosPorCategoria(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int idCat = Integer.parseInt(request.getParameter("Id_CatProd"));
		String nombreCat = catProdDao.listarCategoriaProd().stream().filter(c -> c.getId_CatProd() == idCat).findFirst().orElse(null).getNombre_CatProd();
		
		Map<String, Object> filtrosProd = new HashMap<>();
		filtrosProd.put("Id_CatProd", idCat);
		
		ArrayList<Producto> prodsList = productoDao.listProd(filtrosProd);
		
		request.setAttribute("listProducto", prodsList);
		request.setAttribute("categoria", nombreCat);
		request.getRequestDispatcher("ProductosCategoria.jsp").forward(request, response);
	}
	
	private void obtenerUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario usuarioAutenticado = (Usuario) request.getSession().getAttribute("nomUsuario");
		String usuarioAutenticadoJson = gson.toJson(usuarioAutenticado);
		pw.print(usuarioAutenticadoJson);
	}
}
