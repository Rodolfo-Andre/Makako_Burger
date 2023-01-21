package aplicacion.com.interfaces;

import java.util.*;
import aplicacion.com.entity.*;

public interface ReclamosDao {
	public int AgregarReclamo(Reclamo r);
	public ArrayList<Reclamo> listReclamo(HashMap<String, Object> filtros);
	public ArrayList<TipoReclamo> listTipoReclamo();
}
