package aplicacion.com.controller;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.*;
import aplicacion.com.dao.DAOFactory;
import aplicacion.com.entity.*;
import aplicacion.com.utils.Fecha;
import aplicacion.com.interfaces.*;

/**
 * Servlet implementation class PromocionesController
 */
@WebServlet("/CajaController")
public class CajaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CajaController() {
		super();
	}

	private DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
	private ComprobanteDePagoDao cdpDAO = daoFactory.getCDP();
	private EstablecimientoDAO establecimientoDAO = daoFactory.getEstablecimiento();
	private ClienteDAO clienteDAO = daoFactory.getCliente();
	private ZonaDeRepartoDao zonaDAO = daoFactory.getZona();
	private ArrayList<ComprobanteDePago> listaCDP = null;
	private PrintWriter pw;
	private Gson gson = new Gson();

	// Paginación
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
				case "agregarCDP": {
					generarCDP(request, response);
					break;
				}
				case "getInfoObject": {
					obtenerInfoObjeto(request, response);
					break;
				}
				case "listarCDPCli": {
					try {
						listarCDPCli(request, response);
					} catch (ParseException | ServletException | IOException e) {
						e.printStackTrace();
					}
					break;
				}
				default: {
					response.sendRedirect("CajaController?p=" + pag);
					break;
				}
			}
		} else {
			try {
				listarCDP(request, response);
			} catch (ServletException | IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}

	private void listarCDPCli(HttpServletRequest request, HttpServletResponse response) throws ParseException, ServletException, IOException {
		HashMap<String, Object> filtros = new HashMap<>();
		Cliente c = (Cliente) request.getSession().getAttribute("cliente");
	
		String validaFechaInicio = request.getParameter("startDate");
		String validaFechaFinal = request.getParameter("endDate");
		String validaPrecioInicio = request.getParameter("startPrice");
		String validaPrecioFinal = request.getParameter("endPrice");
	
		filtros.put("busqueda", c.getDni());

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

		precioInicio = validaPrecioInicio != null ? validaPrecioInicio : precioInicio;
		if (!precioInicio.isBlank())
			filtros.put("precioInicio", precioInicio);

		precioFinal = validaPrecioFinal != null ? validaPrecioFinal : precioFinal;
		if (!precioFinal.isBlank())
			filtros.put("precioFinal", precioFinal);

		String validaPag = request.getParameter("p");

		if (validaPrecioInicio != null || validaPrecioFinal != null
				|| validaFechaInicio != null || validaFechaFinal != null) {
			validaPag = "1";
		}

		/* ------------------ Proceso de Paginación ------------------ */
		int pagPeticion = 0;
		double mitadMaximoPaginacion = Math.round(maxDePaginacion / 2.0);

		listaCDP = cdpDAO.findAll(filtros);

		System.out.println(listaCDP.size());

		cantTotalDeRegistro = listaCDP.size();
		totalDePaginacion = cantTotalDeRegistro % nroDeRegistroXPag == 0 ? cantTotalDeRegistro / nroDeRegistroXPag
				: (cantTotalDeRegistro / nroDeRegistroXPag) + 1;

		if (validaPag != null && validaPag.matches("^[0-9]+$")
				&& (pagPeticion = Integer.parseInt(validaPag)) <= totalDePaginacion && pagPeticion > 0) {
			pag = pagPeticion;
		}

		if (cantTotalDeRegistro > 0) {
			int desde = (nroDeRegistroXPag * pag) - nroDeRegistroXPag;
			int hasta = (nroDeRegistroXPag * pag) > cantTotalDeRegistro ? cantTotalDeRegistro : nroDeRegistroXPag * pag;

			List<ComprobanteDePago> listaCDPTemporal = listaCDP.subList(desde, hasta);

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
			request.setAttribute("listaCDP", listaCDPTemporal);
		}

		request.setAttribute("pag", pag);
		request.setAttribute("totalDePaginacion", totalDePaginacion);
		request.setAttribute("totalRegistro", listaCDP.size());
		request.setAttribute("totalVenta", cdpDAO.findAll(new HashMap<>()).stream().reduce(0.0,
				(subtotal, element) -> subtotal + element.getPrecTotPedido(), Double::sum));
		request.setAttribute("precioInicio", precioInicio);
		request.setAttribute("precioFinal", precioFinal);
		request.setAttribute("fechaInicio", fechaInicio);
		request.setAttribute("fechaFinal", fechaFinal);
		
		request.getRequestDispatcher("cdpCli.jsp").forward(request, response);
	}

	private void generarCDP(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int metPago = Integer.parseInt(request.getParameter("cboMetPago"));
		int metRecojo = Integer.parseInt(request.getParameter("cboMetRecojo"));
		HojaDeEnvio hoja = new HojaDeEnvio();
		ComprobanteDePago cdp = new ComprobanteDePago();
		
		// Obtener detalle
		@SuppressWarnings("unchecked")
		ArrayList<DetallePedido> DetaPedido = (ArrayList<DetallePedido>) request.getSession().getAttribute("DetallePedido");
		Pedido pedido = new Pedido();
		double precioTotalPedido = 0;
		
		for (DetallePedido detallePedido : DetaPedido) {
			precioTotalPedido += detallePedido.getPrecioPedidoTot();
			System.out.print(detallePedido.getPrecioPedidoTot());
		}
		
		pedido.setPrecTotPedido(precioTotalPedido);
		pedido.setCodMetPago(metPago);
		pedido.setCodMetodoRecojo(metRecojo);
		
		if (metRecojo == 1) {
			String nomZona = request.getParameter("nameZona");
			String tel = request.getParameter("telfCliente");
			HashMap<String, Object> filtros = new HashMap<>();
			ArrayList<ZonaDeReparto> zonas = zonaDAO.listZonas(filtros);
			ZonaDeReparto Zona = zonas.stream().filter(z -> z.getNombreZona().equals(nomZona)).findFirst().orElse(null);
			
			hoja.setCodZonaReparto(Zona.getIdZonareparto());
			hoja.setTelefono(tel);

			cdp.setPrecCostoEnvio((5 * pedido.getPrecTotPedido()) / 100);
			cdp.setPrecTotPedido(cdp.getPrecTotPedido() + cdp.getPrecCostoEnvio());
		}
		
		cdp.setPrecTotPedido(pedido.getPrecTotPedido());
		
		Cliente c = (Cliente) request.getSession().getAttribute("cliente");
		pedido.setIdCliente(c.getIdCliente());
		cdpDAO.AgregarComprobanteDePago(cdp, pedido, DetaPedido, hoja);

		DetaPedido.clear();
		
		request.getSession().setAttribute("DetallePedido", DetaPedido);
		response.sendRedirect("CajaController?type=listarCDPCli&p=1");
	}
	
	private void obtenerInfoObjeto(HttpServletRequest request, HttpServletResponse response) {
		int idCdp = Integer.parseInt(request.getParameter("id"));
		
		ComprobanteDePago cdp = cdpDAO.findAll(new HashMap<>()).stream().filter(e -> e.getCodComprobante() == idCdp).findFirst().orElse(null);
		Establecimiento establecimiento = establecimientoDAO.ListarEstablecimiento().stream().filter(e -> e.getIdEstablecimiento() == cdp.getIdEstablecimiento()).findFirst().orElse(null);
		Cliente cliente = clienteDAO.finAll().stream().filter(c -> c.getDni().equals(cdp.getDni())).findFirst().orElse(null);
	
		JsonObject jsonObject = new JsonObject();
		
		JsonElement cdpJson = gson.toJsonTree(cdp);
		JsonElement establecimientoJson = gson.toJsonTree(establecimiento);
		JsonElement clienteJson = gson.toJsonTree(cliente);
		
		jsonObject.add("cdp", cdpJson);
		jsonObject.add("establecimiento", establecimientoJson);
		jsonObject.add("cliente", clienteJson);
		
		pw.print(jsonObject);
	}

	private void listarCDP(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		HashMap<String, Object> filtros = new HashMap<>();

		String validaBusqueda = request.getParameter("search");
		String validaFechaInicio = request.getParameter("startDate");
		String validaFechaFinal = request.getParameter("endDate");
		String validaPrecioInicio = request.getParameter("startPrice");
		String validaPrecioFinal = request.getParameter("endPrice");

		// Validar los filtros de búsqueda
		busqueda = validaBusqueda != null ? validaBusqueda : busqueda;
		if (!busqueda.isBlank())
			filtros.put("busqueda", busqueda);

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

		precioInicio = validaPrecioInicio != null ? validaPrecioInicio : precioInicio;
		if (!precioInicio.isBlank())
			filtros.put("precioInicio", precioInicio);

		precioFinal = validaPrecioFinal != null ? validaPrecioFinal : precioFinal;
		if (!precioFinal.isBlank())
			filtros.put("precioFinal", precioFinal);

		String validaPag = request.getParameter("p");

		if (validaBusqueda != null || validaPrecioInicio != null || validaPrecioFinal != null
				|| validaFechaInicio != null || validaFechaFinal != null) {
			validaPag = "1";
		}

		/* ------------------ Proceso de Paginación ------------------ */
		int pagPeticion = 0;
		double mitadMaximoPaginacion = Math.round(maxDePaginacion / 2.0);

		listaCDP = cdpDAO.findAll(filtros);

		System.out.println(listaCDP.size());

		cantTotalDeRegistro = listaCDP.size();
		totalDePaginacion = cantTotalDeRegistro % nroDeRegistroXPag == 0 ? cantTotalDeRegistro / nroDeRegistroXPag
				: (cantTotalDeRegistro / nroDeRegistroXPag) + 1;

		if (validaPag != null && validaPag.matches("^[0-9]+$")
				&& (pagPeticion = Integer.parseInt(validaPag)) <= totalDePaginacion && pagPeticion > 0) {
			pag = pagPeticion;
		}

		if (cantTotalDeRegistro > 0) {
			int desde = (nroDeRegistroXPag * pag) - nroDeRegistroXPag;
			int hasta = (nroDeRegistroXPag * pag) > cantTotalDeRegistro ? cantTotalDeRegistro : nroDeRegistroXPag * pag;

			List<ComprobanteDePago> listaCDPTemporal = listaCDP.subList(desde, hasta);

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
			request.setAttribute("listaCDP", listaCDPTemporal);
		}

		request.setAttribute("pag", pag);
		request.setAttribute("totalDePaginacion", totalDePaginacion);
		request.setAttribute("totalRegistro", listaCDP.size());
		request.setAttribute("totalVenta", cdpDAO.findAll(new HashMap<>()).stream().reduce(0.0,
				(subtotal, element) -> subtotal + element.getPrecTotPedido(), Double::sum));
		request.setAttribute("busqueda", busqueda);
		request.setAttribute("precioInicio", precioInicio);
		request.setAttribute("precioFinal", precioFinal);
		request.setAttribute("fechaInicio", fechaInicio);
		request.setAttribute("fechaFinal", fechaFinal);

		request.getRequestDispatcher("caja.jsp").forward(request, response);
	}
}
