package aplicacion.com.model;

import java.sql.*;
import java.util.ArrayList;
import aplicacion.com.entity.MetodoRecojo;
import aplicacion.com.interfaces.MetodoRecojoDAO;
import aplicacion.com.utils.MysqlConexion;

public class MySqlMetodoRecojoDAO implements MetodoRecojoDAO {
	@Override
	public ArrayList<MetodoRecojo> findAll() {
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet rs = null;
		ArrayList<MetodoRecojo> listMetodo = null;

		try {
			connection = MysqlConexion.getConexion();

			caStatement = connection.prepareCall("{ CALL SP_LISTAR_METRECOJO() }");

			rs = caStatement.executeQuery();

			listMetodo = new ArrayList<>();

			while (rs.next()) {
				MetodoRecojo met = new MetodoRecojo();
				
				met.setCodMetodoRecojo(rs.getInt(1));
				met.setNomTipoRecojo(rs.getString(2));

				listMetodo.add(met);
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

		return listMetodo;
	}
}
