package aplicacion.com.model;

import java.sql.*;
import java.util.ArrayList;
import aplicacion.com.entity.Dni;
import aplicacion.com.interfaces.DniDao;
import aplicacion.com.utils.MysqlConexion;

public class MySqlDniDAO implements DniDao{

	public ArrayList<Dni> findAll() {
		ArrayList<Dni> list = new ArrayList<Dni>();
		Connection cn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		
		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_LISTAR_DNI() }";
			
			call=cn.prepareCall(sql);
			rs=call.executeQuery();
			
			while(rs.next()) {
				Dni d=new Dni(); {
					d.setDni(rs.getString("dni"));
				}
				
				list.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (cn != null) cn.close();
				if (call != null) call.close();
				if (rs != null) rs.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
}
