package aplicacion.com.interfaces;

import java.util.ArrayList;
import aplicacion.com.entity.Dni;

public interface DniDao {
	public ArrayList<Dni> findAll();
}
