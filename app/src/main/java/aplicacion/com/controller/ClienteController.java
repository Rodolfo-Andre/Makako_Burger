package aplicacion.com.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import aplicacion.com.dao.DAOFactory;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.*;

/**
 * Servlet implementation class ClienteController
 */
@WebServlet("/ClienteController")
public class ClienteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClienteController() {
        super();
    }
    
    private DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
    private ClienteDAO clientedao = daoFactory.getCliente();
    private DniDao dniDao = daoFactory.getDni();
    private UsuarioDao usuarioDao = daoFactory.getUsuarioModelo();
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String Tipo = req.getParameter("type");
    	
    	switch(Tipo) {
	    	case "RegistrarCliente": {
	    		createCliente(req, resp);
	    		break;
	    	}
	    	default: {
				resp.sendRedirect("Cliente.jsp");
				break;
			}
    	}
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void createCliente(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("txtName");
		String lastname = req.getParameter("txtApellido");
		String dni = req.getParameter("TxtDni");
		String email = req.getParameter("txtCorreo");
		String password = req.getParameter("txtContrasena");
		
		boolean isIncorrect = false;
		ArrayList<Dni> validarDni = dniDao.findAll();
		ArrayList<Usuario> validarCorreo = usuarioDao.findAll();
		
		for(Usuario u : validarCorreo) {
			if(u.getCorreo().equals(email)) {
				req.setAttribute("ValidarCorreo", "El correo que ingreso ya se encuentra registrado.");
				isIncorrect = true;
			}
		}
		
		for(Dni d: validarDni) {
			if(d.getDni().equals(dni)) {
				req.setAttribute("DniMensaje", "El dni que ingreso ya se encuentra registrado.");
				isIncorrect = true;
			}
		}
		
		if (isIncorrect) {
			req.getRequestDispatcher("Cliente.jsp").forward(req, resp);
			return;
		}
	
		Cliente cliente = new Cliente();
		
		cliente.setNomCliente(name);
		cliente.setApeCliente(lastname);
		cliente.setDni(dni);
		
		Usuario usuario = new Usuario();
		
		usuario.setCorreo(email);
		usuario.setContraseña(password);
	
		System.out.println("Correo: " + usuario.getCorreo());
		System.out.println("Contraseña: " + usuario.getContraseña());
		
		int resultado = clientedao.createCliente(cliente, usuario);
		
		if(resultado != 0) {	
			resp.sendRedirect("login.jsp");
		}else {
			req.setAttribute("mensaje","Ocurrio un problema al registrar al Cliente");
			req.getRequestDispatcher("Cliente.jsp").forward(req, resp);
		}
	}
}
