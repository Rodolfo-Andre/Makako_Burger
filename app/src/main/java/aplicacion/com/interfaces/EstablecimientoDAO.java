package aplicacion.com.interfaces;

import java.util.ArrayList;
import aplicacion.com.entity.Establecimiento;

public interface EstablecimientoDAO {
	public int updateEstablecimiento(Establecimiento e);
	public ArrayList<Establecimiento> ListarEstablecimiento();
}
