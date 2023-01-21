package aplicacion.com.model;

import java.sql.*;
import java.sql.Connection;
import java.util.*;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.ZonaDeRepartoDao;
import aplicacion.com.utils.MysqlConexion;

public class MySqlZonaDeRepartoDAO implements ZonaDeRepartoDao {
	@Override
	public int InsertZonaDeReparto(ZonaDeReparto zona) {
		int resultado = 0;
		Connection cn = null;
		CallableStatement call = null;

		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_INSERT_ZONADEREPARTO(?, ?, ?, ?) }";
			
			call = cn.prepareCall(sql);
			
			call.setString(1, zona.getNombreZona());
			call.setString(2, zona.getDistri().getNomDistrito());
			call.setString(3, zona.getLatitud());
			call.setString(4, zona.getLongitud());

			resultado = call.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cn != null) cn.close();
				if (call != null) call.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return resultado;
	}

	@Override
	public int DeleteZonaDeReparto(int zona) {
		int resultado = 0;
		Connection cn = null;
		CallableStatement call = null;

		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_DELETEZONAREPARTO(?) }";
			
			call = cn.prepareCall(sql);
			
			call.setInt(1,zona);

			resultado = call.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cn != null) cn.close();
				if (call != null) call.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return resultado;
	}

	@Override
	public ArrayList<ZonaDeReparto> listZonas(Map<String, Object> filtros) {
		Connection cn = null;
		CallableStatement call = null;
		ResultSet rs = null;	
		ArrayList<ZonaDeReparto> listar = null;
		
		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_LISTAR_ZONADEREPARTO(?,?) }";
			
			call=cn.prepareCall(sql);
			
			call.setString(1, filtros.containsKey("idDistrito")? filtros.get("idDistrito").toString():"");
			call.setString(2, filtros.containsKey("busqueda")? filtros.get("busqueda").toString():"");
			
			rs = call.executeQuery();
			
			listar=new ArrayList<ZonaDeReparto>();
			
			while(rs.next()) {
				ZonaDeReparto z = new ZonaDeReparto();
				
				z.setIdZonareparto(rs.getInt("idZonaReparto"));
				z.setNombreZona(rs.getString("nombreZona"));
				z.setLatitud(rs.getString("latitud"));
				z.setLongitud(rs.getString("longitud"));

				Distrito distrito = new Distrito();
				distrito.setIdDistrito(rs.getInt("idDistrito"));
				distrito.setNomDistrito(rs.getString("nomDistrito"));
				
				z.setDistri(distrito);
			
				listar.add(z);
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
		
		return listar;
	}
}