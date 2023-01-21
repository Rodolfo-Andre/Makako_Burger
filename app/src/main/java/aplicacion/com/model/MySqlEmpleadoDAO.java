package aplicacion.com.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.EmpleadoDao;
import aplicacion.com.utils.*;

public class MySqlEmpleadoDAO implements EmpleadoDao{
	@Override
	public int agregarEmpleado(Empleados emp) {
		Connection connection = null;
		CallableStatement caStatement = null;
		int estado = 0;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_AGREGAR_EMPLEADO(?, ?, ?, ?, ?, ?, ?, ?) }");
			 
			caStatement.setString(1, emp.getUsuario().getCorreo());
			caStatement.setString(2, emp.getUsuario().getContraseña());
			caStatement.setString(3, emp.getNom_Empleado());
			caStatement.setString(4, emp.getApe_Empleado());
			caStatement.setString(5, emp.getTelfEmpleado());
			caStatement.setString(6, emp.getDNI_Empleado());
			caStatement.setInt(7, emp.getCargo().getIdCargo());
			caStatement.setDate(8, new Date(new java.util.Date().getTime()));
			
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
		
		return estado;
	}

	@Override
	public int updateEmpleado(Empleados emp) {
		Connection connection = null;
		CallableStatement caStatement = null;
		int estado = 0;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_ACTUALIZAR_EMPLEADO(?, ?, ?, ?, ?, ?, ?) }");
		
			caStatement.setInt(1, emp.getUsuario().getIdUsuario());
			caStatement.setInt(2, emp.getIdEmpleado());
			caStatement.setString(3, emp.getUsuario().getCorreo());
			caStatement.setString(4, emp.getNom_Empleado());
			caStatement.setString(5, emp.getApe_Empleado());
			caStatement.setString(6, emp.getTelfEmpleado());
			caStatement.setInt(7, emp.getCargo().getIdCargo());
		
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
		
		return estado;
	}

	@Override
	public ArrayList<Empleados> findAll(Map<String, Object> filtros) {
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet resultSet = null;
		ArrayList<Empleados> listaEmpleado = null;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_LISTAR_EMPLEADO(?, ?, ?, ?) }");
			
			caStatement.setString(1, filtros.containsKey("busqueda") ? filtros.get("busqueda").toString() : "");
			caStatement.setString(2, filtros.containsKey("idCargo") ? filtros.get("idCargo").toString() : "");
			caStatement.setDate(3, filtros.containsKey("fechaInicio") ? new Date(((java.util.Date) filtros.get("fechaInicio")).getTime()) : null);
			caStatement.setDate(4, filtros.containsKey("fechaFinal") ? new Date(((java.util.Date) filtros.get("fechaFinal")).getTime()) : null);
			
			resultSet = caStatement.executeQuery();
			
			listaEmpleado = new ArrayList<>();
			
			while (resultSet.next()) {
				Usuario usuario = new Usuario();
				Empleados empleado = new Empleados();
				Cargo cargo = new Cargo();
				
				usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
				usuario.setCorreo(resultSet.getString("correo"));
				
				empleado.setIdEmpleado(resultSet.getInt("ID_Empleado"));
				empleado.setNom_Empleado(resultSet.getString("nom_Empleado"));
				empleado.setApe_Empleado(resultSet.getString("ape_Empleado"));
				empleado.setTelfEmpleado(resultSet.getString("telf_Empleado"));
				
				empleado.setDNI_Empleado(resultSet.getString("Dni"));
				empleado.setFechaRegistro(new java.util.Date(resultSet.getDate("fechaRegistro_Empleado").getTime()));
				
				cargo.setIdCargo(resultSet.getInt("IdCargo"));
				cargo.setCargo(resultSet.getString("Cargo"));
				
				empleado.setUsuario(usuario);
				empleado.setCargo(cargo);
				
				listaEmpleado.add(empleado);
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
		
		return listaEmpleado;
	}

	@Override
	public int getLastIdEmpleado() {
		int id = 0;
		Connection cn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		
		try {
			cn = MysqlConexion.getConexion();
			String sql = "{ CALL SP_GETLASTIDEMPLEADO() }";
			
			cstm = cn.prepareCall(sql);
			rs = cstm.executeQuery();
			
			if (rs.next()) id = rs.getInt("ID_Empleado");
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
	public int deleteEmpleado(int idEmp, int idUsu, String Dni) {
		Connection connection = null;
		CallableStatement caStatement = null;
		int estado = 0;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_ELIMINAR_EMPLEADO(?, ?, ?) }");
		
			caStatement.setInt(1, idUsu);
			caStatement.setInt(2, idEmp);
			caStatement.setString(3, Dni);
			
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
		
		return estado;
	}
}
