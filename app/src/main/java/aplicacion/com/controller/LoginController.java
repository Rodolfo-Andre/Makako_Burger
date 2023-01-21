package aplicacion.com.controller;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.JsonObject;
import aplicacion.com.dao.DAOFactory;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.*;
import aplicacion.com.utils.*;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
    }

    private DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
    private UsuarioDao usuarioDao = daoFactory.getUsuarioModelo();
    private ClienteDAO clienteDAO = daoFactory.getCliente();
    private EmpleadoDao empleadoDAO = daoFactory.getEmpleadoModelo();
    private PrintWriter pw;
    private JsonObject jsObject = null;
   
    private String correo;
    private int codigo = 0;
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {   	
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");

    	pw = response.getWriter();
    	
		String tipo = request.getParameter("type");		
		
		if (tipo == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		jsObject = new JsonObject();
		
		switch (tipo) {
			case "login": {
				login(request, response);
				break;
			}
			case "logout": {
				logout(request, response);
				break;
			}
			case "verifyEmail": {
				verificarCorreo(request, response);
				break;
			}
			case "sendCode": {
				codigo = Utilidades.random(1000, 5000);
				
				System.out.println(codigo);
				enviarCorreo(request, response);
				break;
			}
			case "verifyCode": {
				verificarCodigo(request, response);
				break;
			}
			case "changePassword": {
				cambiarContraseña(request, response);
				break;
			}
			default: {
				response.sendRedirect("login.jsp");
				break;
			}
		} 
		
		pw.print(jsObject);
    }
    
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.getSession().invalidate();
		response.sendRedirect("indexUser.jsp");
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		String usuarioCorreo = request.getParameter("email");
		String contraseña = request.getParameter("password");

		Usuario usuarioAutenticado = usuarioDao.iniciarSesion(usuarioCorreo, contraseña);

		if (usuarioAutenticado != null) {
			SessionProject sessionproject = new SessionProject();
			
			sessionproject.saveSessionTimeOut(request, 300);

			if (usuarioAutenticado.getIdTipoUsuario() == 1) {
				Empleados empleado = empleadoDAO.findAll(new HashMap<>()).stream().filter(e -> e.getDNI_Empleado().equals(usuarioAutenticado.getDni())).findFirst().orElse(null);
				
				sessionproject.saveSessionString(request, "empleado", empleado);
				sessionproject.saveSessionString(request, "nomUsuario", usuarioAutenticado);
				response.sendRedirect("indexAdmin.jsp");
			} else if (usuarioAutenticado.getIdTipoUsuario() == 2) {
				Cliente cliente = clienteDAO.finAll().stream().filter(c -> c.getDni().equals(usuarioAutenticado.getDni())).findFirst().orElse(null);
				ArrayList<DetallePedido> DetaPedido = new ArrayList<DetallePedido>();
				
				sessionproject.saveSessionString(request, "cliente", cliente);
				sessionproject.saveSessionString(request, "nomUsuario", usuarioAutenticado);
				sessionproject.saveSessionString(request, "DetallePedido", DetaPedido);
				response.sendRedirect("indexUser.jsp");
			}
		} else {
			request.setAttribute("invalid", true);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	private void enviarCorreo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		Thread enviarCorreo = new Thread(new Correo(correo, "Tu código de verificación es: " + codigo, "Código de Verificaión"));
		enviarCorreo.start();
	}
	
	private void verificarCorreo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		correo = request.getParameter("email");

		boolean esCorrecto = usuarioDao.correoEsCorrecto(correo);
		
		if (esCorrecto) {
			System.out.println("Envío correo");
			
			codigo = Utilidades.random(1000, 5000);
			
			enviarCorreo(request, response);
			
			System.out.println(codigo);
	
			jsObject.addProperty("isCorrect", true);		
		} else {
			jsObject.addProperty("isCorrect", false);		
		}
	}
	
	private void verificarCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		int verificarCodigo = Integer.parseInt(request.getParameter("code"));
		
		if (verificarCodigo == codigo) {
			jsObject.addProperty("isCorrect", true);		
		} else {
			jsObject.addProperty("isCorrect", false);		
		}
	}
	
	private void cambiarContraseña(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		String contraseña = request.getParameter("password");
		
		int filasAfectadas = usuarioDao.cambiarContraseña(contraseña, correo);
		
		if (filasAfectadas != 0) {
			jsObject.addProperty("isCorrect", true);		
		} else {
			jsObject.addProperty("isCorrect", false);		
		}
	}
}
