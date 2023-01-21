package aplicacion.com.utils;

import java.sql.*;

public class MysqlConexion {
	public static Connection getConexion() {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://root:root@localhost:3306/Makako_Burger?useSSL=false&useTimezone=true&serverTimezone=UTC";
			
			con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println("Error >> de conexión con la BD" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error >> general : " + e.getMessage());
		}
		
		return con;
	}

	public static void closeConexion(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Problemas al cerrar la conexion");
		}
	}
}