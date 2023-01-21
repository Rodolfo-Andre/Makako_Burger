package aplicacion.com.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.PedidoDAO;
import aplicacion.com.utils.Fecha;
import aplicacion.com.utils.MysqlConexion;

public class MySqlPedidoDAO implements PedidoDAO{
	Fecha tiempo = new Fecha();
	GregorianCalendar calendar = new GregorianCalendar();

	@Override
	public ArrayList<Pedido> findAll(Map<String, Object> filtros) {
		ArrayList<Pedido> lista = new ArrayList<Pedido>();
		Connection cn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		
		try {
			cn = MysqlConexion.getConexion();

			cstm = cn.prepareCall("{ CALL SP_LISTAR_PEDIDOS(?, ?, ?, ?) }");
			
			cstm.setString(1, filtros.containsKey("busqueda") ? filtros.get("busqueda").toString() : "");
			cstm.setDate(2, filtros.containsKey("fechaInicio") ? new Date(((java.util.Date) filtros.get("fechaInicio")).getTime()) : null);
			cstm.setDate(3, filtros.containsKey("fechaFinal") ? new Date(((java.util.Date) filtros.get("fechaFinal")).getTime()) : null);
			cstm.setString(4, filtros.containsKey("idEstado") ? filtros.get("idEstado").toString() : "");
			
			rs = cstm.executeQuery();

			while (rs.next()) {
				Pedido com = new Pedido();
				
				com.setCodPedido(rs.getInt(1));
				com.setFechaPedido(rs.getDate(2, calendar));
				com.setIdCliente(rs.getInt(3));
				com.setPrecTotPedido(rs.getDouble(4));
				com.setCodEstadoPedido(rs.getInt(5));
				com.setCodMetodoRecojo(rs.getInt(6));
				com.setCodMetPago(rs.getInt(7));
				
				EstadosPedido estado = new EstadosPedido();
				
				estado.setCodEstadoPedido(com.getCodEstadoPedido());
				estado.setNomTipoEstado(rs.getString(8));
				com.setEstados(estado);
				
				MetodoPago metPago = new MetodoPago();
				
				metPago.setCodMetPago(com.getCodMetPago());
				metPago.setNomTipoPago(rs.getString(9));
				com.setMetodoPago(metPago);
				
				MetodoRecojo metRecojo = new MetodoRecojo();
				
				metRecojo.setCodMetodoRecojo(com.getCodMetodoRecojo());
				metRecojo.setNomTipoRecojo(rs.getString(10));
				com.setMetodo(metRecojo);
				
				Cliente cli = new Cliente();
				
				cli.setIdCliente(com.getIdCliente());
				cli.setNomCliente(rs.getString(11));
				cli.setApeCliente(rs.getString(12));
				com.setCliente(cli);
				
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

	@Override
	public ArrayList<DetallePedido> findAllDetallePedidoByCodPedido(int codPedido) {
		ArrayList<DetallePedido> lista = new ArrayList<DetallePedido>();
		Connection cn = null;
		CallableStatement cstm = null;
		CallableStatement cstm2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {
			cn = MysqlConexion.getConexion();
			String sql = "{ CALL SP_LISTAR_DETALLE_PEDIDOS_PRODUCTOS(?) }";

			cstm = cn.prepareCall(sql);
			cstm.setString(1, codPedido == 0 ? "" : String.valueOf(codPedido));

			rs = cstm.executeQuery();

			while (rs.next() == true) {
				DetallePedido detalle = new DetallePedido();
				
				detalle.setNumDetalle(rs.getInt(1));
				detalle.setCodPedido(rs.getInt(2));
				detalle.setCodProd(rs.getInt(3));
				detalle.setCodPromo(0);
				detalle.setCant(rs.getInt(4));
				detalle.setPrecioPedidoTot(rs.getDouble(5));
				
				Producto prod = new Producto();
				
				prod.setNomPro(rs.getString(6));
				prod.setPrecioPro(rs.getDouble(7));
				prod.setId_CatProd(8);
				
				CategoriaProducto cat = new CategoriaProducto();
				
				cat.setId_CatProd(prod.getId_CatProd());
				cat.setNombre_CatProd(rs.getString(9));
				
				prod.setCatProducto(cat);
				detalle.setProduct(prod);
				prod.setImagenProd(rs.getString(10));
				lista.add(detalle);
			}
			
			String sqlPromo = "{ CALL SP_LISTAR_DETALLE_PEDIDOS_PROMOS(?) }";
			cstm2 = cn.prepareCall(sqlPromo);
			cstm2.setString(1, codPedido == 0 ? "" : String.valueOf(codPedido));
			
			rs2 = cstm2.executeQuery();
			
			while (rs2.next()) {
				DetallePedido detalle = new DetallePedido();
				
				detalle.setNumDetalle(rs2.getInt(1));
				detalle.setCodPedido(rs2.getInt(2));
				detalle.setCodProd(0);
				detalle.setCodPromo(rs2.getInt(3));
				detalle.setCant(rs2.getInt(4));
				detalle.setPrecioPedidoTot(rs2.getDouble(5));
				
				Promocion promo = new Promocion();
				
				promo.setCodPromo(detalle.getCodPromo());
				promo.setPrecioPromo(rs2.getDouble(6));
				promo.setNomPromo(rs2.getString(7));
				promo.setFechaVigencia(rs2.getDate(8));
				promo.setImagenCombo(rs2.getString(9));
				detalle.setPromo(promo);
				
				lista.add(detalle);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (rs2 != null) rs2.close();
				if (cstm != null) cstm.close();
				if (cstm2 != null) cstm2.close();
				if (cn != null) cn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return lista;
	}

	@Override
	public int actualizarPedido(int estado, int codPedido) {
		Connection connection = null;
		CallableStatement caStatement = null;
		int result = 0;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_ACTUALIZAR_PEDIDO(?, ?) }");
		
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
}
