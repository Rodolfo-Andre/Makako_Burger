package aplicacion.com.interfaces;

import java.util.ArrayList;
import aplicacion.com.entity.Usuario;

public interface UsuarioDao {
	public Usuario iniciarSesion(String usuario, String contra);
	public boolean correoEsCorrecto(String correo);
	public int cambiarContraseña(String contraseña, String correo);
	public ArrayList<Usuario> findAll();
}
