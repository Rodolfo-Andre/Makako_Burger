package aplicacion.com.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.ReclamosDao;
import aplicacion.com.utils.*;

public class MySqlReclamoDAO implements ReclamosDao {
	Fecha fecha = new Fecha();
	Calendar fechaObtener = new GregorianCalendar();

	@Override
	public int AgregarReclamo(Reclamo r) {
		int estado = -1;
		Connection cn = null;
		CallableStatement cs = null;
		
		try {
			cn = MysqlConexion.getConexion();
			cs = cn.prepareCall("{ CALL SP_REGISTRAR_RECLAMO(?, ?, ?, ?, ?) }");

			cs.setInt(1, r.getIdPedido());
			cs.setDate(2, new Date(r.getFechaReclamo().getTime()));
			cs.setInt(3, r.getIdCliente());
			cs.setInt(4, r.getIdReclamo());
			cs.setString(5, r.getDscReclamo());

			estado = cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cs != null) cs.close();
				if (cn != null) cn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return estado;
	}

	@Override
	public ArrayList<Reclamo> listReclamo(HashMap<String, Object> filtros) {
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet resultSet = null;
		ArrayList<Reclamo> listaReclamo = null;
			
		try {
			connection = MysqlConexion.getConexion();
				
			caStatement = connection.prepareCall("{ CALL SP_LISTAR_RECLAMO(?, ?, ?, ?) }");
				
			//Si filtros tiene la llave de busqueda, entonces, obtiene la llave busqueda y la vuelve a entero -> Si es que no hay nada, unas comillas vacias
			caStatement.setString(1, filtros.containsKey("busqueda") ? filtros.get("busqueda").toString() : "");
			caStatement.setString(2, filtros.containsKey("idTipoReclamo") ? filtros.get("idTipoReclamo").toString() : "");
			caStatement.setDate(3, filtros.containsKey("fechaInicio") ? new Date(((java.util.Date) filtros.get("fechaInicio")).getTime()) : null);
			caStatement.setDate(4, filtros.containsKey("fechaFinal") ? new Date(((java.util.Date) filtros.get("fechaFinal")).getTime()) : null);

			resultSet = caStatement.executeQuery();
				
			listaReclamo = new ArrayList<>();
				
			while (resultSet.next()) {
				Reclamo reclamo = new Reclamo();
					
				reclamo.setNumReclamo(resultSet.getInt("numReclamo"));
				reclamo.setIdPedido(resultSet.getInt("idPedido"));
				reclamo.setFechaReclamo(resultSet.getDate("fechaReclamo"));
				reclamo.setIdCliente(resultSet.getInt("idCliente"));
				reclamo.setDniCliente(resultSet.getString("DNI"));
				reclamo.setDscReclamo(resultSet.getString("descripcionReclamo"));
				reclamo.setIdTipoReclamo(resultSet.getInt("idTipoReclamo"));
				reclamo.setTipoReclamo(resultSet.getString("nomTipoReclamo"));
					
				listaReclamo.add(reclamo);
			}
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
		
		return listaReclamo;
	}

	@Override
	public ArrayList<TipoReclamo> listTipoReclamo() {
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet resultSet = null;
		ArrayList<TipoReclamo> listaTipoReclamo = null;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_LISTAR_TIPO_RECLAMO() }");
			
			resultSet = caStatement.executeQuery();
			
			listaTipoReclamo = new ArrayList<>();
			
			while (resultSet.next()) {
				TipoReclamo tipoReclamo = new TipoReclamo();
				
				tipoReclamo.setIdTipoReclamo(resultSet.getInt("idTipoReclamo"));
				tipoReclamo.setNomTipoReclamo(resultSet.getString("nomTipoReclamo"));
				
				listaTipoReclamo.add(tipoReclamo);
			}
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
		
		return listaTipoReclamo;
	}
}
