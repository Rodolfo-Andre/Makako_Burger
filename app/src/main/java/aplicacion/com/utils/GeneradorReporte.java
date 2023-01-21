package aplicacion.com.utils;

import java.io.*;
import java.util.HashMap;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class GeneradorReporte {
	public static JasperPrint genera(InputStream report, JRBeanCollectionDataSource dataSource, HashMap<String, Object> parameters) {
		JasperPrint jasperPrint = null;
		
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(report);
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);

			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		return jasperPrint;
	}
}
