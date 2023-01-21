package aplicacion.com.controller;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import aplicacion.com.dao.DAOFactory;
import aplicacion.com.entity.ReporteCDP;
import aplicacion.com.interfaces.ComprobanteDePagoDao;
import aplicacion.com.utils.GeneradorReporte;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Servlet implementation class ReportesController
 */
@WebServlet("/ReportesController")
public class ReportesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportesController() {
		super();
	}

	private DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
	private ComprobanteDePagoDao cdpDAO = daoFactory.getCDP();

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tipo = request.getParameter("type");

		if (tipo != null) {
			switch (tipo) {
				case "reporteCDP": {
					try {
						obtenerInfoObjeto(request, response);
					} catch (IOException | JRException e) {
						e.printStackTrace();
					}
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	private void obtenerInfoObjeto(HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {
		response.setContentType("application/pdf");
		response.addHeader("Content-disposition", "inline; filename=CDP.pdf");
		
		int idCdp = Integer.parseInt(request.getParameter("id"));

		ServletOutputStream out = response.getOutputStream();
		ArrayList<ReporteCDP> reporte = cdpDAO.generarReporte(idCdp);
		JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(reporte);

		InputStream report = this.getServletConfig().getServletContext().getResourceAsStream("ReporteCDP.jasper");
		JasperPrint jasper = GeneradorReporte.genera(report, data, null);
		
		JasperExportManager.exportReportToPdfStream(jasper, out);
		
		out.flush();
		out.close();
	}	
}
