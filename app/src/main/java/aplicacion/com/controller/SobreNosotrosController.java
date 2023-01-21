package aplicacion.com.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import aplicacion.com.dao.DAOFactory;
import aplicacion.com.entity.Establecimiento;
import aplicacion.com.interfaces.EstablecimientoDAO;

/**
 * Servlet implementation class SobreNosotrosController
 */
@WebServlet("/SobreNosotrosController")
public class SobreNosotrosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SobreNosotrosController() {
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
		 request.getRequestDispatcher("SobreNosotros.jsp").forward(request, response);
	}
}
