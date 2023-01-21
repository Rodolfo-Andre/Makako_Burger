package aplicacion.com.model;

import java.sql.*;
import java.util.ArrayList;
import aplicacion.com.entity.Cargo;
import aplicacion.com.interfaces.CargoDao;
import aplicacion.com.utils.MysqlConexion;

public class MySqlCargoDAO implements CargoDao {
	@Override
	public ArrayList<Cargo> listarCargo() {
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet resultSet = null;
		ArrayList<Cargo> listaCargo = null;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_LISTAR_CARGO() }");
		
			resultSet = caStatement.executeQuery();
			
			listaCargo = new ArrayList<>();
			
			while (resultSet.next()) {
				Cargo cargo = new Cargo();
				
				cargo.setIdCargo(resultSet.getInt("IdCargo"));
				cargo.setCargo(resultSet.getString("cargo"));
				
				listaCargo.add(cargo);
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
		
		return listaCargo;
	}
}
