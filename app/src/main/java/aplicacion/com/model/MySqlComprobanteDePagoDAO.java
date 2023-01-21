package aplicacion.com.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.ComprobanteDePagoDao;
import aplicacion.com.utils.MysqlConexion;

public class MySqlComprobanteDePagoDAO implements ComprobanteDePagoDao {
	@Override
	public int AgregarComprobanteDePago(ComprobanteDePago c, Pedido p, ArrayList<DetallePedido> dt, HojaDeEnvio he) {
		int salida = -1;
		Connection cn = null;
		CallableStatement cstm1 = null;
		CallableStatement cstm2 = null;
		CallableStatement cstm3 = null;
		CallableStatement cstm4 = null;

		try {
			cn = MysqlConexion.getConexion();
			cn.setAutoCommit(false);

			String sqlPedido = "{ CALL SP_AGREGAR_PEDIDO(?, ?, ?, ?) }";
			cstm1 = cn.prepareCall(sqlPedido);
			cstm1.setInt(1, p.getIdCliente());
			cstm1.setDouble(2, p.getPrecTotPedido());
			cstm1.setInt(3, p.getCodMetodoRecojo());
			cstm1.setInt(4, p.getCodMetPago());

			salida = cstm1.executeUpdate();

			String sqlDetallePedido = "{ CALL SP_AGREGAR_DETALLE_PEDIDO(?, ?, ?, ?) }";
			for (DetallePedido detallePedido : dt) {
				cstm2 = cn.prepareCall(sqlDetallePedido);

				// Si tiene el producto, lo setea, si no, da null
				if (!(detallePedido.getCodProd() == 0)) cstm2.setInt(1, detallePedido.getCodProd());
				else cstm2.setNull(1, Types.INTEGER);

				if (!(detallePedido.getCodPromo() == 0)) cstm2.setInt(2, detallePedido.getCodPromo());
				else cstm2.setNull(2, Types.INTEGER);

				cstm2.setInt(3, detallePedido.getCant());
				cstm2.setDouble(4, detallePedido.getPrecioPedidoTot());

				salida = cstm2.executeUpdate();
			}
			
			if(p.getCodMetodoRecojo() == 1) {
				String sqlHoja = "{ CALL SP_AGREGAR_HOJAENVIO(?, ?) }";
				cstm4 = cn.prepareCall(sqlHoja);
				cstm4.setInt(1, he.getCodZonaReparto());
				cstm4.setString(2, he.getTelefono());
				salida = cstm4.executeUpdate();
			}
			
			String sqlCDP = "{ CALL SP_AGREGAR_CDP(?, ?) }";
			cstm3 = cn.prepareCall(sqlCDP);
			cstm3.setDouble(1, c.getPrecTotPedido());
			cstm3.setDouble(2, c.getPrecCostoEnvio());
			salida = cstm3.executeUpdate();

			cn.commit();
		} catch (Exception e) {
			try {
				cn.rollback();
				salida = -1;
				e.printStackTrace();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			e.printStackTrace();
		} finally {
			try {
				if (cstm1 != null) cstm1.close();
				if (cstm2 != null) cstm2.close();
				if (cstm3 != null) cstm3.close();
				if (cstm4 != null) cstm4.close();
				if (cn != null) cn.close();
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}

		return salida;
	}

	@Override
	public ArrayList<ComprobanteDePago> findAll(HashMap<String, Object> filtros) {
		ArrayList<ComprobanteDePago> lista = null;
		Connection cn = null;
		CallableStatement call = null;
		ResultSet rs = null;

		try {
			cn = MysqlConexion.getConexion();

			String sql = "{ CALL SP_LISTAR_CDP(?, ?, ?, ?, ?) }";

			call = cn.prepareCall(sql);

			call.setString(1, filtros.containsKey("busqueda") ? filtros.get("busqueda").toString() : "");

			call.setDate(2, filtros.containsKey("fechaInicio") ? new Date(((java.util.Date) filtros.get("fechaInicio")).getTime()) : null);

			call.setDate(3, filtros.containsKey("fechaFinal") ? new Date(((java.util.Date) filtros.get("fechaFinal")).getTime()) : null);

			if (filtros.containsKey("precioInicio")) call.setDouble(4, Double.parseDouble((String) filtros.get("precioInicio")));
			else call.setNull(4, Types.DOUBLE);

			if (filtros.containsKey("precioFinal")) call.setDouble(5, Double.parseDouble((String) filtros.get("precioFinal")));
			else call.setNull(5, Types.DOUBLE);

			rs = call.executeQuery();

			lista = new ArrayList<ComprobanteDePago>();

			while (rs.next()) {
				ComprobanteDePago com = new ComprobanteDePago();

				com.setCodComprobante(rs.getInt(1));
				com.setIdEstablecimiento(rs.getInt(2));
				com.setCodPedido(rs.getInt(3));
				com.setFchEmitido(rs.getDate(4));
				com.setPrecTotPedido(rs.getDouble(5));
				com.setPrecCostoEnvio(rs.getDouble(6));
				com.setDni(rs.getString(7));

				lista.add(com);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (call != null) call.close();
				if (cn != null) cn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}

		return lista;
	}

	@Override
	public ArrayList<ReporteCDP> generarReporte(int idCDP) {
		ArrayList<ReporteCDP> lista = new ArrayList<ReporteCDP>();
		Connection cn = null;
		CallableStatement cstm = null;
		CallableStatement cstm2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {
			cn = MysqlConexion.getConexion();
			String sql = "{ CALL SP_GENERAR_CDP_REPORTE_PRODUCTOS(?) }";

			cstm = cn.prepareCall(sql);
			cstm.setInt(1, idCDP);

			rs = cstm.executeQuery();

			while (rs.next()) {
				ReporteCDP det1 = new ReporteCDP();
				
				det1.setCodPedido(rs.getInt(1));
				det1.setDniCli(rs.getString(2));
				det1.setNomMetPago(rs.getString(3));
				det1.setNomMetRecojo(rs.getString(4));
				det1.setNom(rs.getString(5));
				det1.setPrecioPro(rs.getDouble(6));
				det1.setPrecioPedidoTot(rs.getDouble(7));
				det1.setFechaEmision(rs.getDate(8));
				det1.setCant(rs.getInt(9));
							
				lista.add(det1);
			}
			
			String sqlPromo = "{ CALL SP_GENERAR_CDP_REPORTE_PROMOCIONES(?) }";
			
			cstm2 = cn.prepareCall(sqlPromo);
			cstm2.setInt(1, idCDP);
			
			rs2 = cstm2.executeQuery();
			
			while (rs2.next()) {
				ReporteCDP det1 = new ReporteCDP();
				
				det1.setCodPedido(rs2.getInt(1));
				det1.setDniCli(rs2.getString(2));
				det1.setNomMetPago(rs2.getString(3));
				det1.setNomMetRecojo(rs2.getString(4));
				det1.setNom(rs2.getString(5));
				det1.setPrecioPro(rs2.getDouble(6));
				det1.setPrecioPedidoTot(rs2.getDouble(7));
				det1.setFechaEmision(rs2.getDate(8));
				det1.setCant(rs2.getInt(9));
							
				lista.add(det1);
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
}
