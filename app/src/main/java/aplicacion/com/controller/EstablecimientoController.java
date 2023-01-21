package aplicacion.com.controller;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import aplicacion.com.dao.DAOFactory;
import aplicacion.com.interfaces.EstablecimientoDAO;
import aplicacion.com.entity.Establecimiento;

/**
 * Servlet implementation class Establecimeinto
 */
@WebServlet("/Establecimiento")
public class EstablecimientoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EstablecimientoController() {
        super();
    }

    private DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
    private EstablecimientoDAO  establecimeintoDao = daoFactory.getEstablecimiento();    
	private ArrayList<Establecimiento> listarEstablecimientos = null;
	
	 @Override
	 public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setCharacterEncoding("UTF-8");
		 String tipo = request.getParameter("type");
			
	    if (tipo != null) {
	    	switch (tipo) {
				case "updateInfoObject": {
					actualizarEstablecimiento(request, response);
					break;
				}
				case "listarinfo": {
	    			ListarEstablecimiento(request, response);
	    			break;
	    		}
	    	}
	   	} 
	 }
	 
	 private void ListarEstablecimiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 listarEstablecimientos = establecimeintoDao.ListarEstablecimiento();
		 request.setAttribute("ListarEstablecimiento", listarEstablecimientos);
		 request.getRequestDispatcher("Establecimiento.jsp").forward(request, response);
	}

	private void actualizarEstablecimiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		String SobreNosotros = request.getParameter("txtSobreNosotros").trim();
			
		Establecimiento establecimiento = new Establecimiento();
		establecimiento.setIdEstablecimiento(1);
		establecimiento.setSobreNosotros(SobreNosotros);
			
		establecimeintoDao.updateEstablecimiento(establecimiento);
		response.sendRedirect("Establecimiento?type=listarinfo");
	}
}
