package aplicacion.com.model;

import java.sql.*;
import java.util.ArrayList;
import aplicacion.com.entity.CategoriaProducto;
import aplicacion.com.interfaces.CatProdDao;
import aplicacion.com.utils.MysqlConexion;

public class MySqlCategoriaProducto implements CatProdDao {
	@Override
	public ArrayList<CategoriaProducto> listarCategoriaProd() {
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet resultSet = null;
		ArrayList<CategoriaProducto> listaCategoriaProd = null;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_LISTAR_CATEGORIA_PROD() }");
				
			resultSet = caStatement.executeQuery();
			
			listaCategoriaProd = new ArrayList<>();
			
			while (resultSet.next()) {
				CategoriaProducto categoriaProd = new CategoriaProducto();
				
				categoriaProd.setId_CatProd(resultSet.getInt("Id_CatProd"));
				categoriaProd.setNombre_CatProd(resultSet.getString("nombre_CatProd"));
				
				listaCategoriaProd.add(categoriaProd);
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
		
		return listaCategoriaProd;
	}
}
