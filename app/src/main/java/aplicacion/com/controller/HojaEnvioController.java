package aplicacion.com.controller;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.Gson;
import aplicacion.com.dao.DAOFactory;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.*;

/**
 * Servlet implementation class HojaEnvioController
 */
@WebServlet("/HojaEnvioController")
public class HojaEnvioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HojaEnvioController() {
		super();
	}

	private DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
	private EstadosPedidoDAO estadoDAO = daoFactory.getEstadoPedido();
	private HojaDeEnvioDao hojaDao = daoFactory.getHojaEnvio();
	private ZonaDeRepartoDao zonaDAO = daoFactory.getZona();
	private PrintWriter pw;
	private Gson gson = new Gson();

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
	private String estadoPedido = "";

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		pw = response.getWriter();
		String tipo = request.getParameter("type");

		if (tipo != null) {
			switch (tipo) {
				case "getInfoObject": {
					obtenerInfoObjeto(request, response);
					break;
				}
				case "updateInfoObject": {
					actualizarObjeto(request, response);
					break;
				}
				default: {
					response.sendRedirect("HojaEnvioController?p=" + pag);
					break;
				}
			}
		} else {
			listarInfo(request, response);
		}
	}

	private void actualizarObjeto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int idPedido = Integer.parseInt(request.getParameter("id"));
		hojaDao.updateHojaDeEnvio(2, idPedido);
		response.sendRedirect("HojaEnvioController?p=" + pag);
	}

	private void obtenerInfoObjeto(HttpServletRequest request, HttpServletResponse response) {
		int idPedido = Integer.parseInt(request.getParameter("id"));
		Map<String, Object> filtros = new HashMap<>();
		HojaDeEnvio hoja = hojaDao.findAll(filtros).stream().filter(h -> h.getCodPedido() == idPedido).findFirst().orElse(null);
		
		if(hoja != null) {
			String pedidoJsonString = gson.toJson(hoja);
			pw.print(pedidoJsonString);
		}
	}

	private void listarInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> filtros = new HashMap<>();
		
		String validaBusqueda = request.getParameter("busqueda");
		String validaEstado = request.getParameter("cboEstado");
		
		if(validaBusqueda != null) {
			if(validaBusqueda.equals("all")) {
				busqueda = "";
				filtros.put("idZona", busqueda);
			}else {
				busqueda = validaBusqueda;
				filtros.put("idZona", busqueda);
			}		
		}
		
		if (validaEstado != null) {
			if (validaEstado.equals("all")) {
				estadoPedido = "";
				filtros.put("idEstado", estadoPedido);
			} else {
				estadoPedido = validaEstado;
				filtros.put("idEstado", estadoPedido);
			}
		}

		String validaPag = request.getParameter("p");

		if (validaBusqueda != null || validaEstado != null) {
			validaPag = "1";
		}

		/* ------------------ Proceso de Paginación ------------------ */
		int pagPeticion = 0;
		double mitadMaximoPaginacion = Math.round(maxDePaginacion / 2.0);
		ArrayList<EstadosPedido> listEstados = estadoDAO.findAll();
		ArrayList<HojaDeEnvio> listHojas = hojaDao.findAll(filtros);
		
		Map<String, Object> filtrosZonas = new HashMap<>();
		ArrayList<ZonaDeReparto> listZonas = zonaDAO.listZonas(filtrosZonas);
		

		cantTotalDeRegistro = listHojas.size();
		totalDePaginacion = cantTotalDeRegistro % nroDeRegistroXPag == 0 ? cantTotalDeRegistro / nroDeRegistroXPag
				: (cantTotalDeRegistro / nroDeRegistroXPag) + 1;

		if (validaPag != null && validaPag.matches("^[0-9]+$")
				&& (pagPeticion = Integer.parseInt(validaPag)) <= totalDePaginacion && pagPeticion > 0) {
			pag = pagPeticion;
		}

		if (cantTotalDeRegistro > 0) {
			int desde = (nroDeRegistroXPag * pag) - nroDeRegistroXPag;
			int hasta = (nroDeRegistroXPag * pag) > cantTotalDeRegistro ? cantTotalDeRegistro : nroDeRegistroXPag * pag;

			List<HojaDeEnvio>  listaHojaTemporal = listHojas.subList(desde, hasta);

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
			request.setAttribute("listaHoja", listaHojaTemporal);
		}

		request.setAttribute("listaZonas", listZonas);
		request.setAttribute("pag", pag);
		request.setAttribute("totalDePaginacion", totalDePaginacion);
		request.setAttribute("totalRegistro", listHojas.size());
		request.setAttribute("listaEstados", listEstados);
		request.setAttribute("busqueda", busqueda);
		request.setAttribute("estadoB", estadoPedido);
		request.getRequestDispatcher("actualizarHojaEnvio.jsp").forward(request, response);
	}
}
