package aplicacion.com.model;

import java.sql.*;
import java.util.*;
import aplicacion.com.entity.CategoriaProducto;
import aplicacion.com.entity.Producto;
import aplicacion.com.interfaces.ProductoDao;
import aplicacion.com.utils.MysqlConexion;

public class MySqlProductoDAO implements ProductoDao {
	@Override
	public int agregarProducto(Producto p) {
		int salida = -1;
		Connection cn = null;
		CallableStatement cstm = null;

		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_AGREGAR_PROD(?, ?, ?, ?, ?) }";
			
			cstm = cn.prepareCall(sql);
			
			cstm.setInt(1, p.getCodProd());
			cstm.setString(2, p.getNomPro());
			cstm.setDouble(3, p.getPrecioPro());
			cstm.setString(4, p.getImagenProd());
			cstm.setInt(5, p.getId_CatProd());
			
			salida = cstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cstm != null) cstm.close();
				if (cn != null) cn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return salida;
	}

	@Override
	public int updateProducto(Producto p) {
		int salida = -1;
		Connection cn = null;
		CallableStatement cstm = null;

		try {
			cn = MysqlConexion.getConexion();
			cstm = cn.prepareCall("{ CALL SP_ACTUALIZAR_PROD(?, ?, ?, ?, ?) }");

			cstm.setInt(1, p.getCodProd());
			cstm.setString(2, p.getNomPro());
			cstm.setDouble(3, p.getPrecioPro());
			cstm.setString(4, p.getImagenProd() != null ? p.getImagenProd() : null);
			cstm.setInt(5, p.getId_CatProd());
			
			salida = cstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cstm != null) cstm.close();
				if (cn != null) cn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return salida;
	}

	@Override
	public int deleteProducto(int codPro) {
		int salida = -1;
		Connection cn = null;
		CallableStatement cstm = null;

		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_ELIMINAR_PROD(?) }";
			
			cstm = cn.prepareCall(sql);
			cstm.setInt(1, codPro);
			
			salida = cstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cstm != null) cstm.close();
				if (cn != null) cn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return salida;
	}

	@Override
	public int getLastId() {
		int id = 0;
		Connection cn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		
		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_GETLASTIDPROD() }";
			
			cstm = cn.prepareCall(sql);
			
			rs = cstm.executeQuery();
			
			if (rs.next()) id = rs.getInt("codPro");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (cstm != null) cstm.close();
				if (rs != null) rs.close();
				if (cn != null) cn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return id;
	}

	@Override
	public ArrayList<Producto> listProd(Map<String, Object> filtros) {
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet resultSet = null;
		ArrayList<Producto> listaProducto = null;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_LISTAR_PRODUCTO(?, ?, ?, ?) }");
			
			//Si filtros tiene la llave de busqueda, entonces, obtiene la llave busqueda y la vuelve a entero -> Si es que no hay nada, unas comillas vacias
			caStatement.setString(1, filtros.containsKey("busqueda") ? filtros.get("busqueda").toString() : "");
			caStatement.setString(2, filtros.containsKey("Id_CatProd") ? filtros.get("Id_CatProd").toString() : "");
			
			//Si es que hay precioInicio, setea con el precio de inicio y setea el string a Double
			if (filtros.containsKey("precioInicio")) caStatement.setDouble(3, Double.parseDouble((String) filtros.get("precioInicio")));
			//En el parametro, quiero que lo especifiques en Nulo y qué tipo de dato era.
			else caStatement.setNull(3, Types.DOUBLE);
			
			if (filtros.containsKey("precioFinal")) caStatement.setDouble(4, Double.parseDouble((String) filtros.get("precioFinal")));
			else caStatement.setNull(4, Types.DOUBLE);
			
			resultSet = caStatement.executeQuery();
			
			listaProducto = new ArrayList<>();
			
			while (resultSet.next()) {
				Producto producto = new Producto();
				
				producto.setCodProd(resultSet.getInt("codPro"));
				producto.setNomPro(resultSet.getString("nomPro"));
				producto.setPrecioPro(resultSet.getDouble("precioPro"));
				producto.setImagenProd(resultSet.getString("imagenProd"));
				producto.setId_CatProd(resultSet.getInt("Id_CatProd"));

				CategoriaProducto categoriaProducto = new CategoriaProducto();
				
				categoriaProducto.setId_CatProd(resultSet.getInt("Id_CatProd"));
				categoriaProducto.setNombre_CatProd(resultSet.getString("nombre_CatProd"));

				producto.setCatProducto(categoriaProducto);
				
				listaProducto.add(producto);
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
		
		return listaProducto;
	}
}
