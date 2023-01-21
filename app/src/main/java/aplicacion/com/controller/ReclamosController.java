package aplicacion.com.controller;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.*;
import aplicacion.com.dao.DAOFactory;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.*;
import aplicacion.com.utils.Fecha;

/**
 * Servlet implementation class ReclamosController
 */
@WebServlet("/ReclamosController")
public class ReclamosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReclamosController() {
		super();
	}

	private DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
	private Fecha fecha = new Fecha();
	private ReclamosDao reclamosDao = daoFactory.getReclamos();
	private PedidoDAO pedidoDao = daoFactory.getPedido();
	private ComprobanteDePagoDao cdpDao = daoFactory.getCDP();
	private ClienteDAO clienteDao = daoFactory.getCliente();
	
	private ArrayList<Reclamo> listaReclamo = null;
	private ArrayList<TipoReclamo> listaTipoReclamo = reclamosDao.listTipoReclamo();
	private PrintWriter pw;
	private Gson gson = new Gson();
	
	// Pagina
	private int pag = 1;
	private int nroDeRegistroXPag = 5;
	private int maxDePaginacion = 5;
	private int totalDePaginacion = 0;
	private int comienza = 0;
	private int termina = 0;
	private int cantTotalDeRegistro = 0;

	// Variables de busqueda
	private String busqueda = "";
	private String tipoReclamoB = "";
	private String fechaInicio = "";
	private String fechaFinal = "";
	
	//RECLAMO REGISTRADO
	private boolean registro = false;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		pw = response.getWriter();
		String tipo = request.getParameter("type");
		
		if (tipo != null) {
			switch (tipo) {
				case "enviarReclamo":
					registrarReclamo(request, response);
					break;
				case "obtenerInfor": {
					obtenerInforReclamo(request, response);
					break;
				}
				case "getInfoObject": {
					obtenerInfoObjetoJS(request, response);
					break;
				}
				case "validarNroPedido": {
					validarNroPedido(request, response);
					break;
				}
				default:{
					response.sendRedirect("ReclamoController?p=" + pag);
					break;
				}	
			}
		} else {
    		try {
    			listarReclamo(request, response);
			} catch (ServletException | IOException | ParseException e) {
				e.printStackTrace();
			}
    	}
    }
	
	private void validarNroPedido(HttpServletRequest request, HttpServletResponse response) {
		String idPedido = request.getParameter("id");
		String dni = request.getParameter("dni");
		
		HashMap<String, Object> filtros = new HashMap<>();
		filtros.put("busqueda", dni);
		
		boolean encontroNroPedido = cdpDao.findAll(filtros).stream().anyMatch(cdp -> String.valueOf(cdp.getCodPedido()).equals(idPedido));
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("encontroNroPedido", encontroNroPedido);
		
		pw.print(encontroNroPedido);
	}
	
	private void listarReclamo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
		HashMap<String, Object> filtros = new HashMap<>();
	
		String validaBusqueda = request.getParameter("search");
		String validaTipoReclamo = request.getParameter("cboTipoReclamo");
		String validaFechaInicio = request.getParameter("startDate");
		String validaFechaFinal = request.getParameter("endDate");
		
		//Validar los filtros de busqueda
		busqueda = validaBusqueda != null ? validaBusqueda : busqueda;
		if (!busqueda.isBlank()) filtros.put("busqueda", busqueda);
	
		if (validaTipoReclamo != null) {
			if (validaTipoReclamo.equals("all")) tipoReclamoB = "";
			else tipoReclamoB = validaTipoReclamo;
		}
		
		if (!tipoReclamoB.isBlank()) filtros.put("idTipoReclamo", tipoReclamoB);
	
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

		if (validaBusqueda != null || validaTipoReclamo != null || validaFechaInicio != null || validaFechaFinal != null) {
			validaPag = "1";
		}

		/* ------------------ Proceso de Paginaci�n ------------------ */
		int pagPeticion = 0;
		double mitadMaximoPaginacion = Math.round(maxDePaginacion / 2.0);

		listaReclamo = reclamosDao.listReclamo(filtros);

		cantTotalDeRegistro = listaReclamo.size();
		totalDePaginacion = cantTotalDeRegistro % nroDeRegistroXPag == 0 ? cantTotalDeRegistro / nroDeRegistroXPag
				: (cantTotalDeRegistro / nroDeRegistroXPag) + 1;

		if (validaPag != null && validaPag.matches("^[0-9]+$")
				&& (pagPeticion = Integer.parseInt(validaPag)) <= totalDePaginacion && pagPeticion > 0) {
			pag = pagPeticion;
		}

		if (cantTotalDeRegistro > 0) {
			int desde = (nroDeRegistroXPag * pag) - nroDeRegistroXPag;
			int hasta = (nroDeRegistroXPag * pag) > cantTotalDeRegistro ? cantTotalDeRegistro : nroDeRegistroXPag * pag;

			List<Reclamo> listaReclamoTemporal = listaReclamo.subList(desde, hasta);

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
			request.setAttribute("listaReclamo", listaReclamoTemporal);
		}

		request.setAttribute("pag", pag);
		request.setAttribute("totalDePaginacion", totalDePaginacion);
		request.setAttribute("totalRegistro", listaReclamo.size());
		request.setAttribute("listaTipoReclamo", listaTipoReclamo);
		request.setAttribute("busqueda", busqueda);
		request.setAttribute("tipoReclamoB", tipoReclamoB);
		request.setAttribute("fechaInicio", fechaInicio);
		request.setAttribute("fechaFinal", fechaFinal);
		
		request.getRequestDispatcher("reporteReclamo.jsp").forward(request, response);
	}

	private void obtenerInfoObjetoJS(HttpServletRequest request, HttpServletResponse response) {
		int idReclamo = Integer.parseInt(request.getParameter("id"));
		
		Reclamo reclamo = reclamosDao.listReclamo(new HashMap<>()).stream().filter(r -> r.getNumReclamo() == idReclamo).findFirst().orElse(null);
		Cliente cliente = clienteDao.finAll().stream().filter(c -> c.getDni().equals(reclamo.getDniCliente())).findFirst().orElse(null);
	
		JsonObject jsonObject = new JsonObject();
		
		JsonElement reclamoJson = gson.toJsonTree(reclamo);
		JsonElement clienteJson = gson.toJsonTree(cliente);
		
		jsonObject.add("reclamo", reclamoJson);
		jsonObject.add("cliente", clienteJson);
		
		pw.print(jsonObject);
	}
	
	private void obtenerInforReclamo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat fecha = new SimpleDateFormat("YYYY-MM-dd");
		String fechaFormateada = fecha.format(calendar.getTime());
		
		request.setAttribute("fechaFormateada", fechaFormateada);
		request.setAttribute("reclamoRegistrado", registro);
		registro = false;
		request.getRequestDispatcher("indexReclamos.jsp").forward(request, response);
	}
	
	private void registrarReclamo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JsonObject jo = new JsonObject();
		HttpSession sesion = request.getSession();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat fecha1 = new SimpleDateFormat("YYYY-MM-dd");
		String fechaFormateada = fecha1.format(calendar.getTime());
		String descripcionReclamo = request.getParameter("detReclamaco");
		Cliente c = (Cliente) sesion.getAttribute("cliente");
		
		if (c != null) {
			int TipoReclamo = Integer.parseInt(request.getParameter("rbTipoReclamo"));
			int idPedido = Integer.parseInt(request.getParameter("codPedido"));
			Map<String, Object> filtrosReclamo = new HashMap<>();

			ArrayList<Pedido> pedido = pedidoDao.findAll(filtrosReclamo);
			
			// .stream permitirá operar con colecciones y leerlas de manera rápida
			Pedido p = pedido.stream().filter(a -> a.getCodPedido() == idPedido).findFirst().orElse(null);
			System.out.println(idPedido);

			if (p.getIdCliente() == c.getIdCliente()) {
				Reclamo r = new Reclamo();
				r.setIdPedido(idPedido);
				r.setFechaReclamo(fecha.StringADate(fechaFormateada));
				r.setIdCliente(c.getIdCliente());
				r.setIdReclamo(TipoReclamo);
				r.setDscReclamo(descripcionReclamo);
				
				reclamosDao.AgregarReclamo(r);
				
				registro = true;
					
				jo.addProperty("reclamoRegistrado", true);
					
				response.sendRedirect("ReclamosController?type=obtenerInfor");		
			} else {
				jo.addProperty("errorReclamo", true);
			}
		} else {
			jo.addProperty("errorCliente", true);
		}
	}
}
