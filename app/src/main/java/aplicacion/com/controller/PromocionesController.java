package aplicacion.com.controller;

import java.io.*;
import java.nio.file.*;
import java.text.ParseException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.Part;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import aplicacion.com.dao.DAOFactory;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.*;
import aplicacion.com.utils.Fecha;

/**
 * Servlet implementation class PromocionesController
 */
@WebServlet("/PromocionesController")
@MultipartConfig()
public class PromocionesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PromocionesController() {
		super();
	}

	private DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
	private Fecha fechaUtil = new Fecha();
	private PromocionDAO promoDao = daoFactory.getPromocion();
	private PedidoDAO pedidoDAO = daoFactory.getPedido();
	private ArrayList<Promocion> listarPromo = null;
	
	private PrintWriter pw;
	private Gson gson = new Gson();
	
	// Ruta de la imagen
	private String rutaPromo = "";

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
	private String precioInicio = "";
	private String precioFinal = "";
	private String fechaInicio = "";
	private String fechaFinal = "";

	@Override
	public void init() throws ServletException {
		rutaPromo = getServletContext().getRealPath("") + "img/promociones";
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tipo = request.getParameter("type");
		pw = response.getWriter();
		
		if (tipo != null) {
			switch (tipo) {
				case "addInfoObject": {
					añadirInfoObjeto(request, response);
					break;
				}
				case "getPromoObject": {
					obtenerPromo(request, response);
					break;
				}
				case "updatePromo": {
					actualizarPromo(request, response);
					break;
				}
				case "deletePromo": {
					eliminarPromocion(request, response);
					break;
				}
				case "getListDetallePromo": {
					obtenerDetallePromo(request, response);
					break;
				}
				case "updateDetallePromo": {
					actualizarDetallePromo(request, response);
					break;
				}
				case "listDetallePromo": {
					listDetallePromo(request, response);
					break;
				}
				case "validarActDetPromo": {
					validarActDetPromo(request, response);
					break;
				}
				case "findPromotionDetailsInOrders": {
					validarPromocionEnPedido(request, response);
					break;
				}
				default: {
					response.sendRedirect("PromocionesController?p=" + pag);
					break;
				}
			}
		} else {
			try {
				listarPromo(request, response);
			} catch (ServletException | IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void validarPromocionEnPedido(HttpServletRequest request, HttpServletResponse response) {
		int idPromo = Integer.parseInt(request.getParameter("id"));
		
		boolean encontroPromocionEnPedido = pedidoDAO.findAllDetallePedidoByCodPedido(0).stream().anyMatch(pd -> pd.getCodPromo() == idPromo);
		
		JsonObject jsObject = new JsonObject();			
		jsObject.addProperty("foundPromotionInOrders", encontroPromocionEnPedido);

		pw.print(jsObject);
	}

	private void validarActDetPromo(HttpServletRequest request, HttpServletResponse response) {
		int tipoOP = Integer.parseInt(request.getParameter("tipoOP"));
		int idPromo = Integer.parseInt(request.getParameter("id"));
		JsonObject jsObject = new JsonObject();

		switch (tipoOP) {
			case 1: {
				int codProd = Integer.parseInt(request.getParameter("cboProd"));
				DetallePromocion dt = promoDao.findAllDetallePromoByidPromo(idPromo).stream().filter(d -> d.getCodPro() == codProd).findFirst().orElse(null);
				
				if(dt != null) {
					jsObject.addProperty("prodRepet", "Producto ya se encuentra registrado");
				}
				
				break;
			}
			case 3: {
				ArrayList<DetallePromocion> det = promoDao.findAllDetallePromoByidPromo(idPromo);
				
				if(det.size() - 1 <= 0) {
					jsObject.addProperty("minimo", "Debe haber como mínimo un producto");
				}
				
				break;
			}
		}
		
		pw.print(jsObject);
	}

	private void actualizarDetallePromo(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int tipoOP = Integer.parseInt(request.getParameter("tipoOP"));
		int idPromo = Integer.parseInt(request.getParameter("id"));
		int codProd = 0;
		int cant = 0;

		if (tipoOP == 1) {
			cant = Integer.parseInt(request.getParameter("cantProd"));
			codProd = Integer.parseInt(request.getParameter("cboProd"));
		}else if (tipoOP == 2){
			cant = Integer.parseInt(request.getParameter("cantProd"));
			codProd = Integer.parseInt(request.getParameter("codProd"));
		}else {
			codProd = Integer.parseInt(request.getParameter("codProd"));
		}
		
		DetallePromocion detalle = new DetallePromocion();
		detalle.setCantidad(cant);
		detalle.setCodPro(codProd);
		detalle.setCodPromo(idPromo);
		promoDao.modificarDetallePromocion(detalle, tipoOP);
		
		response.sendRedirect("PromocionesController?type=listDetallePromo&id="  + idPromo);
	}

	private void actualizarPromo(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String nomPromo = request.getParameter("namePromo");
		Double precioPromo = Double.parseDouble(request.getParameter("pricePromo"));
		String fechaPromo = request.getParameter("datePromo");
		int codPromo = Integer.parseInt(request.getParameter("id"));

		String nombreArchivo = listarPromo.stream().filter(p -> p.getCodPromo() == codPromo).findFirst().get()
				.getImagenCombo();
		
		// Si la imagen es nula, no la actualiza
		boolean imagenEsNoNulo = false;
		
		// Manejar imagen
		Part imagen = request.getPart("image");
		
		// Es para validar que la imagen que está llegando del modal es mayor a 0, es
		// decir, que se ingreso una imagen
		// Si es que se ingreso, la cambia, si es que no, usa la anterior
		if (imagenEsNoNulo = imagen.getSize() > 0) {
			File validaCarpeta = new File(rutaPromo);
			// Si es que la carpeta no existe, entonces la crea
			if (!validaCarpeta.exists()) {
				validaCarpeta.mkdir();
			}
			
			InputStream imagenBytes = imagen.getInputStream();
			File archivoImagen = new File(validaCarpeta.getAbsolutePath() + "/" + nombreArchivo + ".png");
			Files.copy(imagenBytes, archivoImagen.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}

		Promocion promo = new Promocion();
		promo.setCodPromo(codPromo);
		promo.setNomPromo(nomPromo);
		promo.setPrecioPromo(precioPromo);
		promo.setFechaVigencia(fechaUtil.StringADate(fechaPromo));
		
		if (imagenEsNoNulo) promo.setImagenCombo(nombreArchivo);
		
		promoDao.actualizarPromocion(promo);
		response.sendRedirect("PromocionesController?p=1");
	}

	private void listDetallePromo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int idPromo = Integer.parseInt(request.getParameter("id"));
		Promocion promo = listarPromo.stream().filter(p -> p.getCodPromo() == idPromo).findFirst().orElse(null);
		
		if (promo != null) {
			ArrayList<DetallePromocion> detalle = promoDao.findAllDetallePromoByidPromo(idPromo);

			request.setAttribute("promocion", promo);
			request.setAttribute("detalle", detalle);
			request.getRequestDispatcher("actualizarPromociones.jsp").forward(request, response);
		}
	}

	private void obtenerDetallePromo(HttpServletRequest request, HttpServletResponse response) {
		int idPromo = Integer.parseInt(request.getParameter("id"));
		ArrayList<DetallePromocion> detalle = promoDao.findAllDetallePromoByidPromo(idPromo);
		String listDetalle = gson.toJson(detalle);
		pw.print(listDetalle);
	}

	private void obtenerPromo(HttpServletRequest request, HttpServletResponse response) {
		int idPromo = Integer.parseInt(request.getParameter("id"));	
		Promocion promo = listarPromo.stream().filter(p -> p.getCodPromo() == idPromo).findFirst().orElse(null);
		
		if (promo != null) {
			String prodJsonString = gson.toJson(promo);
			pw.print(prodJsonString);
		}
	}

	private void eliminarPromocion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String nombreArchivo = listarPromo.stream().filter(p -> p.getCodPromo() == id).findFirst().get()
				.getImagenCombo();
		File borrarImagen = new File(rutaPromo + "/" + nombreArchivo + ".png");

		Files.deleteIfExists(borrarImagen.toPath());
		promoDao.deletePromocion(id);
		
		response.sendRedirect("PromocionesController?p=" + pag);
	}

	private void añadirInfoObjeto(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String nomPro = request.getParameter("namePromo");
		double precioPro = Double.parseDouble(request.getParameter("pricePromo"));
		String fechaVigencia = request.getParameter("datePromo");

		// Obtener la imagen del objeto part
		Part imagen = request.getPart("image");
		
		// Obtener imagen en flujo de bytes
		InputStream imagenBytes = imagen.getInputStream();

		// Validamos la carpeta, sino exite la carpeta, entonces la creamos //La ruta
		// está en init() del inicio del Servlet
		// Dicha ruta no se muestra en el proyecto, es carpeta creada por el servidor.
		File validaCarpeta = new File(rutaPromo);
		if (!validaCarpeta.exists()) validaCarpeta.mkdir();

		// Crear copia de imagen y almacenar en la ruta especificada
		String nombreArchivo = String.valueOf(new Date().getTime());
		File archivoImagen = new File(validaCarpeta.getAbsolutePath() + "/" + nombreArchivo + ".png");
		Files.copy(imagenBytes, archivoImagen.toPath(), StandardCopyOption.REPLACE_EXISTING);

		Promocion promo = new Promocion();

		promo.setPrecioPromo(precioPro);
		promo.setNomPromo(nomPro);
		promo.setImagenCombo(nombreArchivo);
		promo.setFechaVigencia(fechaUtil.StringADate(fechaVigencia));

		// Variables para detalle
		int idProd = Integer.parseInt(request.getParameter("cboProd"));
		int cantidad = Integer.parseInt(request.getParameter("cantProd"));

		DetallePromocion detalle = new DetallePromocion();
		detalle.setCantidad(cantidad);
		detalle.setCodPro(idProd);

		promoDao.agregarPromocion(promo, detalle);
		response.sendRedirect("PromocionesController?p=" + pag);
	}

	private void listarPromo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		Map<String, Object> filtros = new HashMap<>();

		String validaBusqueda = request.getParameter("search");
		String validaPrecioInicio = request.getParameter("startPrice");
		String validaPrecioFinal = request.getParameter("endPrice");
		String validaFechaInicio = request.getParameter("startDate");
		String validaFechaFinal = request.getParameter("endDate");

		// Validar los filtros de búsqueda
		busqueda = validaBusqueda != null ? validaBusqueda : busqueda;
		if (!busqueda.isBlank()) filtros.put("busqueda", busqueda);

		precioInicio = validaPrecioInicio != null ? validaPrecioInicio : precioInicio;
		if (!precioInicio.isBlank()) filtros.put("precioInicio", precioInicio);

		precioFinal = validaPrecioFinal != null ? validaPrecioFinal : precioFinal;
		if (!precioFinal.isBlank()) filtros.put("precioFinal", precioFinal);

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

		if (validaBusqueda != null || validaPrecioInicio != null || validaPrecioFinal != null
				|| validaFechaInicio != null || validaFechaFinal != null) {
			validaPag = "1";
		}

		/* ------------------ Proceso de Paginación ------------------ */
		int pagPeticion = 0;
		double mitadMaximoPaginacion = Math.round(maxDePaginacion / 2.0);

		listarPromo = promoDao.findAllPromocion(filtros);
		
		cantTotalDeRegistro = listarPromo.size();
		totalDePaginacion = cantTotalDeRegistro % nroDeRegistroXPag == 0 ? cantTotalDeRegistro / nroDeRegistroXPag
				: (cantTotalDeRegistro / nroDeRegistroXPag) + 1;

		if (validaPag != null && validaPag.matches("^[0-9]+$")
				&& (pagPeticion = Integer.parseInt(validaPag)) <= totalDePaginacion && pagPeticion > 0) {
			pag = pagPeticion;
		}

		if (cantTotalDeRegistro > 0) {
			int desde = (nroDeRegistroXPag * pag) - nroDeRegistroXPag;
			int hasta = (nroDeRegistroXPag * pag) > cantTotalDeRegistro ? cantTotalDeRegistro : nroDeRegistroXPag * pag;

			List<Promocion> listaPromocionTemporal = listarPromo.subList(desde, hasta);

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
			request.setAttribute("listaPromocion", listaPromocionTemporal);
		}

		request.setAttribute("pag", pag);
		request.setAttribute("totalDePaginacion", totalDePaginacion);
		request.setAttribute("totalRegistro", listarPromo.size());
		request.setAttribute("busqueda", busqueda);
		request.setAttribute("precioInicio", precioInicio);
		request.setAttribute("precioFinal", precioFinal);
		request.setAttribute("fechaInicio", fechaInicio);
		request.setAttribute("fechaFinal", fechaFinal);
		request.getRequestDispatcher("promociones.jsp").forward(request, response);
	}
}
