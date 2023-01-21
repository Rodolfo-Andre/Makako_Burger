package aplicacion.com.model;

import java.sql.*;
import java.util.ArrayList;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.ClienteDAO;
import aplicacion.com.utils.MysqlConexion;

public class MySqlClienteDao implements ClienteDAO {
	@Override
	public int createCliente(Cliente c, Usuario u) {
		int estado = 0;
		Connection cn = null;
		CallableStatement call = null;
		
		try {
			cn = MysqlConexion.getConexion(); 
			
			String sql = "{ CALL SP_INSERT_CLIENTE(?, ?, ?, ?, ?) }";
			
			call = cn.prepareCall(sql);
			
			call.setString(1, c.getNomCliente());
			call.setString(2, c.getApeCliente());
			call.setString(3, c.getDni());
			call.setString(4, u.getCorreo());
			call.setString(5, u.getContraseña());

			estado = call.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (call != null) call.close();
				if (cn != null) cn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return estado;
	}

	@Override
	public ArrayList<Cliente> finAll() {
		ArrayList<Cliente> bean = new ArrayList<Cliente>();
		Connection cn = null;
		CallableStatement call = null;
		ResultSet rs = null;

		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_LISTAR_CLIENTE() }";
			
			call = cn.prepareCall(sql);
			
			rs = call.executeQuery();

			while (rs.next()) {
				Cliente c = new Cliente();
			
				c.setIdCliente(rs.getInt("idCliente"));
				c.setDni(rs.getString("Dni"));
				c.setNomCliente(rs.getString("nomCliente"));
				c.setApeCliente(rs.getString("apeCliente"));

				bean.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cn != null) cn.close();
				if (call != null) call.close();
				if (rs != null) rs.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return bean;
	}
}
