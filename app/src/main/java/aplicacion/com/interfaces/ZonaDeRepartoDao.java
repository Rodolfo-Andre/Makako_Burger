package aplicacion.com.interfaces;

import java.util.*;
import aplicacion.com.entity.ZonaDeReparto;

public interface ZonaDeRepartoDao {	
	public int InsertZonaDeReparto (ZonaDeReparto zona);
	public int DeleteZonaDeReparto (int zona);
	
	//Nuevo
	public ArrayList<ZonaDeReparto> listZonas(Map<String, Object> filtros);
}
