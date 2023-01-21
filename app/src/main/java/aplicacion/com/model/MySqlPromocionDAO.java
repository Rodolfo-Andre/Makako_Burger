package aplicacion.com.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.PromocionDAO;
import aplicacion.com.utils.*;

public class MySqlPromocionDAO implements PromocionDAO {
	Fecha tiempo = new Fecha();
	GregorianCalendar calendar = new GregorianCalendar();

	@Override
	public int agregarPromocion(Promocion promo, DetallePromocion detalle) {
		Connection connection = null;
		CallableStatement cs = null;
		int estado = 0;
		
		try {
			connection = MysqlConexion.getConexion();
			
			cs = connection.prepareCall("{ CALL SP_AGREGAR_PROMOCION(?, ?, ?, ?, ?, ?) }");
			
			cs.setString(1, promo.getNomPromo());
			cs.setDouble(2, promo.getPrecioPromo());
			cs.setDate(3, tiempo.convertDataToSqlDate(promo.getFechaVigencia()));

			cs.setString(4, promo.getImagenCombo());
			cs.setInt(5, detalle.getCodPro());
			cs.setInt(6, detalle.getCantidad());
			
			estado = cs.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				MysqlConexion.closeConexion(connection);
				if (cs != null && !cs.isClosed()) cs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return estado;
	}

	@Override
	public int actualizarPromocion(Promocion promo) {
		Connection connection = null;
		CallableStatement caStatement = null;
		int estado = 0;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_ACTUALIZAR_PROMO(?, ?, ?, ?, ?) }");
		
			caStatement.setString(1, promo.getNomPromo());
			caStatement.setDouble(2, promo.getPrecioPromo());
			caStatement.setDate(3, tiempo.convertDataToSqlDate(promo.getFechaVigencia()));
			caStatement.setString(4, promo.getImagenCombo() != null ? promo.getImagenCombo() : null);
			caStatement.setInt(5, promo.getCodPromo());
		
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
	public int deletePromocion(int id) {
		Connection connection = null;
		CallableStatement caStatement = null;
		int estado = 0;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_ELIMINAR_PROMO(?) }");
		
			caStatement.setInt(1, id);
		
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
	public int getLastIdCombo() {
		int id = 0;
		Connection cn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		
		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_GETLASTIDPROMO() }";
			
			cstm = cn.prepareCall(sql);
			
			rs = cstm.executeQuery();
			
			if (rs.next()) id = rs.getInt("codPromo");
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
	public int getLastIdDetalleCombo() {
		int id = 0;
		Connection cn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		
		try {
			cn = MysqlConexion.getConexion();
			
			String sql = "{ CALL SP_GETLASTIDDETALLEPROMO() }";
			
			cstm = cn.prepareCall(sql);
			
			rs = cstm.executeQuery();
			
			if (rs.next()) id = rs.getInt("codDetallePromo");
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
	public ArrayList<Promocion> findAllPromocion(Map<String, Object> filtros) {
		Connection connection = null;
		CallableStatement caStatement = null;
		ResultSet rs = null;
		ArrayList<Promocion> listaPromocion = null;

		try {
			connection = MysqlConexion.getConexion();

			caStatement = connection.prepareCall("{ CALL SP_LISTAR_PROMOCION(?, ?, ?, ?, ?) }");

			caStatement.setString(1, filtros.containsKey("busqueda") ? filtros.get("busqueda").toString() : "");
			
			if (filtros.containsKey("precioInicio"))
				caStatement.setDouble(2, Double.parseDouble((String) filtros.get("precioInicio")));
			else caStatement.setNull(2, Types.DOUBLE);

			if (filtros.containsKey("precioFinal"))
				caStatement.setDouble(3, Double.parseDouble((String) filtros.get("precioFinal")));
			else caStatement.setNull(3, Types.DOUBLE);
			
			caStatement.setDate(4, filtros.containsKey("fechaInicio") ? new Date(((java.util.Date) filtros.get("fechaInicio")).getTime()) : null);
			caStatement.setDate(5, filtros.containsKey("fechaFinal") ? new Date(((java.util.Date) filtros.get("fechaFinal")).getTime()) : null);
			
			rs = caStatement.executeQuery();

			listaPromocion = new ArrayList<>();

			while (rs.next()) {
				Promocion promocion = new Promocion();

				promocion.setCodPromo(rs.getInt("codPromo"));
				promocion.setPrecioPromo(rs.getDouble("precioPromo"));
				promocion.setNomPromo(rs.getString("nomPromo"));
				promocion.setFechaVigencia(rs.getDate("fechaVigencia"));
				promocion.setImagenCombo(rs.getString("imagenCombo"));

				listaPromocion.add(promocion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				MysqlConexion.closeConexion(connection);
				if (caStatement != null && !caStatement.isClosed())
					caStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaPromocion;
	}

	@Override
    public ArrayList<DetallePromocion> findAllDetallePromoByidPromo(int idPromo) {
        Connection connection = null;
        CallableStatement caStatement = null;
        ResultSet rs = null;
        ArrayList<DetallePromocion> listaDetallePromocion = null;

        try {
            connection = MysqlConexion.getConexion();

            caStatement = connection.prepareCall("{ CALL SP_LISTAR_DETALLEPROMOCION(?) }");
            
            caStatement.setString(1, idPromo == 0 ? "" : String.valueOf(idPromo));
            rs = caStatement.executeQuery();

            listaDetallePromocion = new ArrayList<>();

            while (rs.next()) {
                DetallePromocion detalle = new DetallePromocion();

                detalle.setCodDetallePromo(rs.getInt(1));
                detalle.setCodPromo(rs.getInt(2));
                detalle.setCodPro(rs.getInt(3));
                detalle.setCantidad(rs.getInt(4));
                
                Producto pro = new Producto();
                pro.setNomPro(rs.getString(5));
                
                CategoriaProducto cat = new CategoriaProducto();
                cat.setId_CatProd(rs.getInt(6));
                cat.setNombre_CatProd(rs.getString(7));
                
                pro.setCatProducto(cat);
                pro.setImagenProd(rs.getString(8));
                detalle.setProd(pro);
                listaDetallePromocion.add(detalle);
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

        return listaDetallePromocion;
    }

	@Override
	public int modificarDetallePromocion(DetallePromocion detalle, int tipoOperacion) {
		Connection connection = null;
		CallableStatement caStatement = null;
		int estado = 0;
		
		try {
			connection = MysqlConexion.getConexion();
			
			caStatement = connection.prepareCall("{ CALL SP_ACTUALIZARDETALLEPROMO(?, ?, ?, ?) }");
		
			caStatement.setInt(1, detalle.getCodPromo());
			caStatement.setInt(2, detalle.getCodPro());
			caStatement.setInt(3, detalle.getCantidad());
			caStatement.setInt(4, tipoOperacion);
			
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
