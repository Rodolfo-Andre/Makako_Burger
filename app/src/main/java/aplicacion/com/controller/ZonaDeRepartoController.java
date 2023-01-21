package aplicacion.com.controller;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.*;
import aplicacion.com.dao.DAOFactory;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.*;

/**
 * Servlet implementation class ZonaDeRepartoController
 */
@WebServlet("/ZonaDeRepartoController")
public class ZonaDeRepartoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Gson gson = new Gson();
	private PrintWriter pw;

	private DAOFactory daofatory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
	private ZonaDeRepartoDao zonaDao = daofatory.getZona();
	private DistritoDao distritoDao = daofatory.getDistrito();
	private HojaDeEnvioDao hojaDeEnvioDao = daofatory.getHojaEnvio();
	private ArrayList<ZonaDeReparto> listzonaReparto = null;
	private ArrayList<Distrito> listDistrito=null;

	// P�gina
	private int pag = 1;
	private int nroDeRegistroXPag = 5;
	private int maxDePaginacion = 5;
	private int totalDePaginacion = 0;
	private int comienza = 0;
	private int termina = 0;
	private int cantTotalDeRegistro = 0;
	
	 // Variable de b�squeda
    private String busqueda = "";
    private String cboDistrito = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ZonaDeRepartoController() {
		super();
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String tipo = request.getParameter("type");
		pw = response.getWriter();

		if (tipo != null) {
			switch (tipo) {
				case "ListarZonaDeReparto": {
					ListaZonaDeReparto(request, response);
					break;
				}
				case "addZonaDeReparto": {
					InsertarDireccion(request, response);
					break;
				}
				case "deleteDirecciones": {
					DeleteDirecciones(request, response);
					break;
				}
				case "ListarDirecciones": {
					ListarDirecciones(request, response);
					break;
				}
				case "obtenerListaZonaRepartos": {
	                getListZonaReparto(request, response);
	                break;
	            }
				case "ValidarEliminarMakako": {
					
				}
				case "obtenerZonaDeReparto": {
					obtenerInfoObjeto(request, response);
					break;
				}
				case "buscarZonaRepartoEnHojaEnvio": {
					buscarZonaRepartoEnHojaEnvio(request, response);
					break;
				}
				default: {
					response.sendRedirect("ZonaDeRepartoController?=p" + pag);
					break;
				}
			}
		} else {
			ListarDirecciones(request, response);
		}
	}
	
	private void buscarZonaRepartoEnHojaEnvio(HttpServletRequest request, HttpServletResponse response) {
		int idZonaReparto = Integer.parseInt(request.getParameter("id"));
		
		boolean encontroZonaReparto = hojaDeEnvioDao.findAll(new HashMap<String, Object>()).stream().anyMatch(he -> he.getCodZonaReparto() == idZonaReparto);
	
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("encontroZonaReparto", encontroZonaReparto);
		
		pw.print(encontroZonaReparto);
	}

    private void getListZonaReparto(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> filtros = new HashMap<>();
        ArrayList<ZonaDeReparto> zonas = zonaDao.listZonas(filtros);

        String listZonas = gson.toJson(zonas);
        System.out.print("Zonas controller" + listZonas);
        pw.print(listZonas);
    }

	protected void ListarDirecciones(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		String validaBusqueda = req.getParameter("search");
		String validaIdDistrito = req.getParameter("cboDistrito");
		String validaPag = req.getParameter("p");
		
		// Indicar
		int pagPeticionProducto = 0;
		
		//Aqu� depende de los filtros, ya que se busca por distrito o nombreZonas,
		// pero no es necesario ambos.
		Map<String, Object> filtrosZonaDeReparto = new HashMap<>();

		if (validaBusqueda != null) {
			busqueda = validaBusqueda;
			validaPag = "1";
		}
		
		// Agrega al diccionario el filtro de b�squeda
		if (!busqueda.isBlank()) filtrosZonaDeReparto.put("busqueda", busqueda);
		
		System.out.print("\n"+busqueda);		
		
		if (validaIdDistrito != null) {
			if (validaIdDistrito.equals("all")) cboDistrito = "";
			else cboDistrito = validaIdDistrito;
		}
		
		if (!cboDistrito.isBlank()) filtrosZonaDeReparto.put("idDistrito", cboDistrito);
		
		System.out.print("\n"+cboDistrito);
		
		listDistrito = distritoDao.finAll();
		listzonaReparto = zonaDao.listZonas(filtrosZonaDeReparto);
		
		// Validar lo de las p�ginas que se van a mostrar y que siempre sea de 0 a 9
		// Y l
		if (validaPag != null && validaPag.matches("^[0-9]+$")
				&& (pagPeticionProducto = Integer.parseInt(validaPag)) <= totalDePaginacion
				&& pagPeticionProducto > 0) {
			pag = pagPeticionProducto;
		}
		
		/* ------------------ Proceso de Paginaci�n ------------------ */
		int pagPeticion = 0;
		double mitadMaximoPaginacion = Math.round(maxDePaginacion / 2.0);

		cantTotalDeRegistro = listzonaReparto.size();
		totalDePaginacion = cantTotalDeRegistro % nroDeRegistroXPag == 0 ? cantTotalDeRegistro / nroDeRegistroXPag
				: (cantTotalDeRegistro / nroDeRegistroXPag) + 1;

		if (cantTotalDeRegistro > 0) {
			int desde = (nroDeRegistroXPag * pag) - nroDeRegistroXPag;
			int hasta = (nroDeRegistroXPag * pag) > cantTotalDeRegistro ? cantTotalDeRegistro : nroDeRegistroXPag * pag;

			List<ZonaDeReparto> listZonaRepartoTemporal = listzonaReparto.subList(desde, hasta);

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

			req.setAttribute("comienza", comienza);
			req.setAttribute("termina", termina);
			req.setAttribute("listaZonas", listZonaRepartoTemporal);
		}
		
		req.setAttribute("pag", pag);
		req.setAttribute("totalDePaginacion", totalDePaginacion);
		req.setAttribute("totalRegistro", listzonaReparto.size());
		req.setAttribute("listDistritos",listDistrito);
		req.setAttribute("busqueda", busqueda);
		req.setAttribute("direcciones", cboDistrito);
		req.getRequestDispatcher("Direcciones.jsp").forward(req, resp);
	}

	protected void ListaZonaDeReparto(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		Map<String, Object> filtrosZonaDeReparto = new HashMap<>();
		listzonaReparto = zonaDao.listZonas(filtrosZonaDeReparto);
		String zona = gson.toJson(listzonaReparto);
		pw.print(zona);
	}

	protected void InsertarDireccion(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		String nombreZona = req.getParameter("txtNombreZona");
		String distrito = req.getParameter("txtDistrito");
		String longitud = req.getParameter("txtLongitud");
		String latitud = req.getParameter("txtLatitud");

		ZonaDeReparto zona = new ZonaDeReparto();
		zona.setNombreZona(nombreZona);
		zona.setLatitud(latitud);
		zona.setLongitud(longitud);
		zona.setDistri(new Distrito() {
			{
				setNomDistrito(distrito);
			}
		});
		
		zonaDao.InsertZonaDeReparto(zona);

		resp.sendRedirect("ZonaDeRepartoController?p=" + pag);
	}
	
	private void obtenerInfoObjeto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		int idZona = Integer.parseInt(request.getParameter("id"));
		ZonaDeReparto zona = listzonaReparto.stream().filter(z -> z.getIdZonareparto() == idZona).findFirst().orElse(null);	
		
		if (zona != null) {
			String mesaJsonString = gson.toJson(zona);
			pw.print(mesaJsonString);
			System.out.println(mesaJsonString);
		}
	}

	protected void DeleteDirecciones(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int IdZonaReparto=Integer.parseInt(req.getParameter("id"));
				
		zonaDao.DeleteZonaDeReparto(IdZonaReparto);
		
		if (pag == totalDePaginacion && pag > 1 && cantTotalDeRegistro - 1 == (pag * nroDeRegistroXPag) - nroDeRegistroXPag) {
			resp.sendRedirect("ZonaDeRepartoController?p=" + (pag - 1));
		} else {
			resp.sendRedirect("ZonaDeRepartoController?p=" + pag);
		}
	}
}
