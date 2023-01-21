package aplicacion.com.interfaces;

import java.util.*;
import aplicacion.com.entity.HojaDeEnvio;

public interface HojaDeEnvioDao {
	public int updateHojaDeEnvio(int estado, int codPedido);
	public ArrayList<HojaDeEnvio> findAll(Map<String, Object> filtros);
}
