package aplicacion.com.model;

import java.sql.*;
import java.util.ArrayList;
import aplicacion.com.entity.MetodoPago;
import aplicacion.com.interfaces.MetodoPagoDAO;
import aplicacion.com.utils.MysqlConexion;

public class MySqlMetodoPagoDAO implements MetodoPagoDAO {
	@Override
	public ArrayList<MetodoPago> findAll() {
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet resultSet = null;
		ArrayList<MetodoPago> listaMet = null;

		try {
			connection = MysqlConexion.getConexion();

			caStatement = connection.prepareCall("{ CALL SP_LISTAR_METPAGO() }");

			resultSet = caStatement.executeQuery();

			listaMet = new ArrayList<>();

			while (resultSet.next()) {
				MetodoPago met = new MetodoPago();

				met.setCodMetPago(resultSet.getInt(1));
				met.setNomTipoPago(resultSet.getString(2));
				
				listaMet.add(met);
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

		return listaMet;
	}
}
