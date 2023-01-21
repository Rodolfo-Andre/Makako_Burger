package aplicacion.com.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.*;
import aplicacion.com.dao.*;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.*;
import aplicacion.com.utils.Fecha;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * Servlet implementation class EmpleadosController
 */
@WebServlet("/EmpleadosController")
public class EmpleadosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmpleadosController() {
		super();
		// TODO Auto-generated constructor stub
	}

	private DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
	private EmpleadoDao empleadoDao = daoFactory.getEmpleadoModelo();
	private CargoDao cargoDao = daoFactory.getCargoModelo();
	private UsuarioDao usuDao = daoFactory.getUsuarioModelo();
	private DniDao dniDao = daoFactory.getDni();
	private ArrayList<Empleados> listaEmpleados = null;
	private ArrayList<Cargo> listaCargo = null;
	private ArrayList<Usuario> listaUsuario = null;
	private PrintWriter pw;
	private Gson gson = new Gson();

	private int pag = 1;
	private int nroDeRegistroXPag = 5;
	private int maxDePaginacion = 5;
	private int totalDePaginacion = 0;
	private int comienza = 0;
	private int termina = 0;
	private int cantTotalDeRegistro = 0;

	// Variables de búsqueda
	private String busqueda = "";
	private String cargoB = "";
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
		
		String tipo = request.getParameter("type");
		pw = response.getWriter();

		if (tipo != null) {
			switch (tipo) {
				case "addInfoObject": {
					anadirInfoObjeto(request, response);
					break;
				}
				case "getInfoObject": {
					obtenerInfoObjeto(request, response);
					break;
				}
				case "updateInfoObject": {
					actualizarObjeto(request, response);
					break;
				}
				case "deleteInfoObject": {
					eliminarObjeto(request, response);
					break;
				}
				case "getListPosition": {
					obtenerListaCargo(request, response);
					break;
				}
				case "validar": {
					validarRegistro(request, response);
					break;
				}
				default: {
					response.sendRedirect("EmpleadosController?p=" + pag);
					break;
				}
			}
		} else {
			try {
				listarEmpleados(request, response);
			} catch (ServletException | IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}

	private void validarRegistro(HttpServletRequest request, HttpServletResponse response) {
		String dni = request.getParameter("dni");
		String correo = request.getParameter("email");

		//Almacenar tipo de errores
		JsonObject jsObject = new JsonObject();
		
		Dni dniValidar = dniDao.findAll().stream().filter(d -> d.getDni().equals(dni)).findFirst().orElse(null);
		if(dniValidar != null) {
			jsObject.addProperty("ErrorDni", "El DNI ya se encuentra registrado");
		}
		
		Usuario usu = usuDao.findAll().stream().filter(u -> u.getCorreo().equals(correo) && !u.getDni().equals(dni)).findFirst().orElse(null);
		if(usu != null) {
			jsObject.addProperty("errorCorreo", "El correo ya se encuentra registrado en la BD");		
		}
		
		pw.print(jsObject);
	}

	private void anadirInfoObjeto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nomEmpleado = request.getParameter("name");
		String apeEmpleado = request.getParameter("lastName");
		String dni = request.getParameter("dni");
		String correo = request.getParameter("email");
		String telefono = request.getParameter("telephone");
		int idCargo = Integer.parseInt(request.getParameter("cboPosition"));

		Empleados emp = new Empleados();
		emp.setNom_Empleado(nomEmpleado);
		emp.setApe_Empleado(apeEmpleado);
		emp.setTelfEmpleado(telefono);
		emp.setDNI_Empleado(dni);
		emp.setCargo(new Cargo() {
			{
				setIdCargo(idCargo);
			}
		});
		
		Usuario usu = new Usuario();
		usu.setCorreo(correo);
		usu.setContraseña(emp.generarContraseña());
		emp.setUsuario(usu);
		
		empleadoDao.agregarEmpleado(emp);
		response.sendRedirect("EmpleadosController?p=" + pag);
	}

	private void listarEmpleados(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		/* ------------------ Variables de Búsqueda ------------------ */
		Map<String, Object> filtros = new HashMap<>();

		String validaBusqueda = request.getParameter("search");
		String validaCargoB = request.getParameter("cboPosition");
		String validaFechaInicio = request.getParameter("startDate");
		String validaFechaFinal = request.getParameter("endDate");

		busqueda = validaBusqueda != null ? validaBusqueda : busqueda;
		if (!busqueda.isBlank()) filtros.put("busqueda", busqueda);

		if (validaCargoB != null) {
			if (validaCargoB.equals("all")) cargoB = "";
			else cargoB = validaCargoB;
		}

		if (!cargoB.isBlank()) filtros.put("idCargo", cargoB);

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

		if (validaBusqueda != null || validaCargoB != null || validaFechaInicio != null || validaFechaFinal != null) {
			validaPag = "1";
		}

		/* ------------------ Proceso de Paginación ------------------ */
		int pagPeticion = 0;
		double mitadMaximoPaginacion = Math.round(maxDePaginacion / 2.0);

		listaCargo = cargoDao.listarCargo();
		listaEmpleados = empleadoDao.findAll(filtros);

		cantTotalDeRegistro = listaEmpleados.size();
		totalDePaginacion = cantTotalDeRegistro % nroDeRegistroXPag == 0 ? cantTotalDeRegistro / nroDeRegistroXPag
				: (cantTotalDeRegistro / nroDeRegistroXPag) + 1;

		if (validaPag != null && validaPag.matches("^[0-9]+$")
				&& (pagPeticion = Integer.parseInt(validaPag)) <= totalDePaginacion && pagPeticion > 0) {
			pag = pagPeticion;
		}

		if (cantTotalDeRegistro > 0) {
			int desde = (nroDeRegistroXPag * pag) - nroDeRegistroXPag;
			int hasta = (nroDeRegistroXPag * pag) > cantTotalDeRegistro ? cantTotalDeRegistro : nroDeRegistroXPag * pag;

			List<Empleados> listaEmpleadoTemporal = listaEmpleados.subList(desde, hasta);

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
			request.setAttribute("listaEmpleado", listaEmpleadoTemporal);
		}

		request.setAttribute("pag", pag);
		request.setAttribute("totalDePaginacion", totalDePaginacion);
		request.setAttribute("totalRegistro", listaEmpleados.size());
		request.setAttribute("listaCargo", listaCargo);
		request.setAttribute("busqueda", busqueda);
		request.setAttribute("cargoB", cargoB);
		request.setAttribute("fechaInicio", fechaInicio);
		request.setAttribute("fechaFinal", fechaFinal);
		request.getRequestDispatcher("mantenimiento-empleado.jsp").forward(request, response);
	}

	private void obtenerListaCargo(HttpServletRequest request, HttpServletResponse response) {
		listaCargo = cargoDao.listarCargo();
		String cargoJsonString = gson.toJson(listaCargo);
		pw.print(cargoJsonString);
	}

	private void eliminarObjeto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (listaEmpleados.size() == 1) return;

		int idEmpleado = Integer.parseInt(request.getParameter("id"));
		Empleados emp = listaEmpleados.stream().filter(e -> e.getIdEmpleado() == idEmpleado).findFirst().orElse(null);

		empleadoDao.deleteEmpleado(idEmpleado, emp.getUsuario().getIdUsuario(), emp.getDNI_Empleado());

		if (pag == totalDePaginacion && pag > 1
				&& cantTotalDeRegistro - 1 == (pag * nroDeRegistroXPag) - nroDeRegistroXPag) {
			response.sendRedirect("EmpleadosController?p=" + (pag - 1));
		} else {
			response.sendRedirect("EmpleadosController?p=" + pag);
		}
	}

	private void actualizarObjeto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int idEmpleado = Integer.parseInt(request.getParameter("id"));
		String nomEmpleado = request.getParameter("name");
		String apeEmpleado = request.getParameter("lastName");
		String correo = request.getParameter("email");
		String telefono = request.getParameter("telephone");

		int idCargo = Integer.parseInt(request.getParameter("cboPosition"));

		Empleados emp = listaEmpleados.stream().filter(e -> e.getIdEmpleado() == idEmpleado).findFirst().orElse(null);
		emp.setNom_Empleado(nomEmpleado);
		emp.setApe_Empleado(apeEmpleado);
		emp.setTelfEmpleado(telefono);
		emp.setCargo(new Cargo() {
			{
				setIdCargo(idCargo);
			}
		});

		listaUsuario = usuDao.findAll();
		Usuario usu = listaUsuario.stream().filter(u -> u.getDni().equals(emp.getDNI_Empleado())).findFirst()
				.orElse(null);
		usu.setCorreo(correo);
		emp.setUsuario(usu);

		empleadoDao.updateEmpleado(emp);
		response.sendRedirect("EmpleadosController?p=" + pag);
	}

	private void obtenerInfoObjeto(HttpServletRequest request, HttpServletResponse response) {
		int idEmp = Integer.parseInt(request.getParameter("id"));
		Empleados emp = listaEmpleados.stream().filter(e -> e.getIdEmpleado() == idEmp).findFirst().orElse(null);
		
		if (emp != null) {
			String empJsonString = gson.toJson(emp);
			pw.print(empJsonString);
		}
	}
}
