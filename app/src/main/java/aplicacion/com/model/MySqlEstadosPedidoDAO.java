package aplicacion.com.model;

import java.sql.*;
import java.util.ArrayList;
import aplicacion.com.entity.EstadosPedido;
import aplicacion.com.interfaces.EstadosPedidoDAO;
import aplicacion.com.utils.MysqlConexion;

public class MySqlEstadosPedidoDAO implements EstadosPedidoDAO{
	@Override
	public ArrayList<EstadosPedido> findAll() {
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet rs = null;
		
		ArrayList<EstadosPedido> listEstado = null;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_LISTAR_ESTADOSPEDIDO() }");
				
			rs = caStatement.executeQuery();
			
			listEstado = new ArrayList<>();
			
			while (rs.next()) {
				EstadosPedido estado = new EstadosPedido();
				
				estado.setCodEstadoPedido(rs.getInt(1));
				estado.setNomTipoEstado(rs.getString(2));
				
				listEstado.add(estado);
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
		
		return listEstado;
	}
}
