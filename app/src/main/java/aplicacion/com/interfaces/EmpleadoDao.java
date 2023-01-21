package aplicacion.com.interfaces;

import java.util.*;
import aplicacion.com.entity.Empleados;

public interface EmpleadoDao {
	public int agregarEmpleado(Empleados emp);
	public int updateEmpleado(Empleados emp);
	public int deleteEmpleado(int idEmp, int idUsu, String Dni);
	public ArrayList<Empleados> findAll(Map<String, Object> filtros);
	public int getLastIdEmpleado();
}
