package aplicacion.com.interfaces;

import java.util.*;
import aplicacion.com.entity.*;

public interface ComprobanteDePagoDao {
	public int AgregarComprobanteDePago (ComprobanteDePago c, Pedido p, ArrayList<DetallePedido> dt, HojaDeEnvio he);
	public ArrayList<ComprobanteDePago> findAll(HashMap<String, Object> filtros);
	public ArrayList<ReporteCDP> generarReporte(int idCDP);
}
