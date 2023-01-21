package aplicacion.com.interfaces;

import java.util.ArrayList;
import aplicacion.com.entity.MetodoPago;

public interface MetodoPagoDAO {
	public ArrayList<MetodoPago> findAll();
}
