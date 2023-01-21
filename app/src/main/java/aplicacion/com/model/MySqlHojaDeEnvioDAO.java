package aplicacion.com.model;

import java.sql.*;
import java.util.*;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.HojaDeEnvioDao;
import aplicacion.com.utils.MysqlConexion;

public class MySqlHojaDeEnvioDAO implements HojaDeEnvioDao {
	@Override
	public int updateHojaDeEnvio(int estado, int codPedido) {
		Connection connection = null;
		CallableStatement caStatement = null;
		int result = 0;

		try {
			connection = MysqlConexion.getConexion();

			caStatement = connection.prepareCall("{ CALL SP_ACTUALIZAR_HOJA_ENVIO(?, ?) }");

			caStatement.setInt(1, estado);
			caStatement.setInt(2, codPedido);

			estado = caStatement.executeUpdate();
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

		return result;
	}

	@Override
	public ArrayList<HojaDeEnvio> findAll(Map<String, Object> filtros) {
		ArrayList<HojaDeEnvio> lista = new ArrayList<HojaDeEnvio>();
		Connection cn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		
		try {
			cn = MysqlConexion.getConexion();

			cstm = cn.prepareCall("{ CALL SP_LISTAR_HOJA_ENVIO(?, ?) }");
			
			cstm.setString(1, filtros.containsKey("idZona") ? filtros.get("idZona").toString() : "");
			cstm.setString(2, filtros.containsKey("idEstado") ? filtros.get("idEstado").toString() : "");
			
			rs = cstm.executeQuery();

			while (rs.next() == true) {
				HojaDeEnvio com = new HojaDeEnvio();
	
				com.setCodHojaEnvio(rs.getInt(1));
				com.setCodPedido(rs.getInt(2));
				com.setCodZonaReparto(rs.getInt(3));
				com.setTelefono(rs.getString(4));
				com.setCodEstadoPedido(rs.getInt(5));
				
				ZonaDeReparto zona = new ZonaDeReparto();
				
				zona.setIdZonareparto(com.getCodZonaReparto());
				zona.setNombreZona(rs.getString(6));
				
				Distrito distri = new Distrito();
				
				distri.setIdDistrito(rs.getInt(7));
				distri.setNomDistrito(rs.getString(8));
				
				zona.setDistri(distri);
				com.setZona(zona);
				
				EstadosPedido estado = new EstadosPedido();
				
				estado.setCodEstadoPedido(com.getCodEstadoPedido());
				estado.setNomTipoEstado(rs.getString(9));
				com.setEstados(estado);

				lista.add(com);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (cstm != null) cstm.close();
				if (cn != null) cn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return lista;
	}
}
