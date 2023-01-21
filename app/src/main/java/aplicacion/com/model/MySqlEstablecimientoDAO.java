package aplicacion.com.model;

import java.sql.*;
import java.util.ArrayList;
import aplicacion.com.entity.*;
import aplicacion.com.utils.MysqlConexion;
import aplicacion.com.interfaces.EstablecimientoDAO;

public class MySqlEstablecimientoDAO implements EstablecimientoDAO {
	@Override
	public int updateEstablecimiento(Establecimiento e) {
		int salida = -1;
		Connection cn = null;
		CallableStatement cltm = null;

		try {
			cn = MysqlConexion.getConexion();
			cltm = cn.prepareCall("{ CALL SP_ACTUALIZAR_ESTABLECIMIENTO(?, ?) }");
		
			cltm.setInt(1, e.getIdEstablecimiento());
			cltm.setString(2, e.getSobreNosotros());

			salida = cltm.executeUpdate();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (cltm != null) cltm.close();
				if (cn != null) cn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return salida;
	}

	@Override
	public ArrayList<Establecimiento> ListarEstablecimiento() {
		ArrayList<Establecimiento> listaestablecimiento = null;
		Connection cn = null;
		CallableStatement cltm = null;
		ResultSet rs = null;

		try {
			cn = MysqlConexion.getConexion();
			
			cltm = cn.prepareCall("{ CALL SP_LISTAR_ESTABLECIMIENTO() }");
			
			rs= cltm.executeQuery();
			
			listaestablecimiento = new ArrayList<>();
			
			while (rs.next()) {
				Establecimiento listaEsta = new  Establecimiento();
				
				listaEsta.setIdEstablecimiento(rs.getInt("idEstablecimiento"));
				listaEsta.setNomEstablecimiento(rs.getString("nomEstablecimiento"));
				listaEsta.setTelefonoEstablecimiento(rs.getString(3));
				listaEsta.setIdZonaReparto(rs.getInt(4));
				listaEsta.setSobreNosotros(rs.getString("SobreNosotros"));
				
				Distrito distri = new Distrito();
				distri.setNomDistrito(rs.getString(6));
				listaEsta.setDistri(distri);
				
				ZonaDeReparto zona = new ZonaDeReparto();
				zona.setNombreZona(rs.getString(7));
				listaEsta.setZona(zona);
				
				listaestablecimiento.add(listaEsta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cn != null) cn.close();
				if (rs != null) rs.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return listaestablecimiento;
	}
}
