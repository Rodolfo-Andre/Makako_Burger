package aplicacion.com.model;

import java.sql.*;
import java.util.ArrayList;
import aplicacion.com.entity.Distrito;
import aplicacion.com.interfaces.DistritoDao;
import aplicacion.com.utils.MysqlConexion;

public class MysqlDistritoDAO implements DistritoDao{
	@Override
	public ArrayList<Distrito> finAll() {
		Connection cn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		ArrayList<Distrito> list = new ArrayList<Distrito>();
		
		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_LISTAR_DISTRITO() }";
			call = cn.prepareCall(sql);
			
			rs = call.executeQuery();
			
			while(rs.next()) {
				Distrito d = new Distrito();
				
				d.setIdDistrito(rs.getInt("idDistrito"));
				d.setNomDistrito(rs.getString("nomDistrito"));
				
				list.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(cn != null) cn.close();
				if(call != null) call.close();
				if(rs != null) rs.close();				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
}
