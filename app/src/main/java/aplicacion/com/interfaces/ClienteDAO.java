package aplicacion.com.interfaces;

import java.util.ArrayList;
import aplicacion.com.entity.*;

public interface ClienteDAO {
	public int createCliente(Cliente cliente, Usuario usuario); 
	public ArrayList<Cliente> finAll();
}
