package aplicacion.com.model;

import java.sql.*;
import java.util.ArrayList;
import aplicacion.com.entity.Usuario;
import aplicacion.com.interfaces.UsuarioDao;
import aplicacion.com.utils.MysqlConexion;

public class MySqlUsuarioDAO implements UsuarioDao{
	@Override
	public Usuario iniciarSesion(String correo, String contra) {
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet resultSet = null;
		Usuario usuario = null;
		
		try {
			connection = MysqlConexion.getConexion();
			
			String sql="{ CALL SP_INICIAR_SESION(?, ?) }";
			
			caStatement=connection.prepareCall(sql);
		
			caStatement.setString(1, correo);
			caStatement.setString(2, contra);
			
			resultSet = caStatement.executeQuery();
			
			if (resultSet.next()) {
				usuario = new Usuario();
				
				usuario.setIdUsuario(resultSet.getInt("idUsuario"));
				usuario.setIdTipoUsuario(resultSet.getInt("idTipoUsuario"));
				usuario.setDni(resultSet.getString("dni"));
				usuario.setCorreo(resultSet.getString("correo"));
				usuario.setContraseña(resultSet.getString("contraseña"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				MysqlConexion.closeConexion(connection);
				if (caStatement != null && !caStatement.isClosed()) caStatement.close();
				if (resultSet != null && !resultSet.isClosed()) resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return usuario;
	}

	@Override
	public boolean correoEsCorrecto(String correo) {
		boolean esCorrecto = false;
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = MysqlConexion.getConexion();
			
			// ResultSet.TYPE_SCROLL_SENSITIVE se desplaza en ambas direcciones y rastrea las actualizaciones en la base de datos a tiempo para cambiar los datos en ResultSet.
			// ResultSet.CONCUR_READ_ONLY solo lee ResultSet
			caStatement = connection.prepareCall("{ CALL SP_BUSCAR_CORREO(?) }", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
			caStatement.setString(1, correo);
		
			resultSet = caStatement.executeQuery();
				
			// Ubicar el cursor en el útlimo resultado de la fila
			if (resultSet.last()) {
				esCorrecto = resultSet.getRow() == 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				MysqlConexion.closeConexion(connection);
				if (caStatement != null && !caStatement.isClosed()) caStatement.close();
				if (resultSet != null && !resultSet.isClosed()) resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return esCorrecto;
	}

	@Override
	public int cambiarContraseña(String contraseña, String correo) {
		Connection connection = null;
		CallableStatement caStatement = null;
		int filasAfectadas = 0;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_CAMBIAR_CONTRASEÑA(?, ?) }");
		
			caStatement.setString(1, contraseña);
			caStatement.setString(2, correo);
		
			filasAfectadas = caStatement.executeUpdate();
			
			System.out.println(filasAfectadas);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				MysqlConexion.closeConexion(connection);
				if (caStatement != null && !caStatement.isClosed()) caStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return filasAfectadas;
	}

	@Override
	public ArrayList<Usuario> findAll() {
		ArrayList<Usuario> lista=new ArrayList<Usuario>();
		Connection cn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		
		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_LISTAR_USUARIO() }";
			
			call = cn.prepareCall(sql);
			
			rs = call.executeQuery();
			
			while(rs.next()) {
				Usuario usuario=new Usuario();
				
				usuario.setIdUsuario(rs.getInt("IdUsuario"));
				usuario.setIdTipoUsuario(rs.getInt("idTipoUsuario"));
				usuario.setDni(rs.getString("Dni"));
				usuario.setCorreo(rs.getString("correo"));
				usuario.setContraseña(rs.getString("contraseña"));
				
				lista.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (cn != null) cn.close();
				if (call != null) call.close();
				if (rs != null) rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return lista;
	}
}
