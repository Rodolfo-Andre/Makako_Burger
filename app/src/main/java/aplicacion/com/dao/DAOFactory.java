package aplicacion.com.dao;

import aplicacion.com.interfaces.CargoDao;
import aplicacion.com.interfaces.CatProdDao;
import aplicacion.com.interfaces.ClienteDAO;
import aplicacion.com.interfaces.ComprobanteDePagoDao;
import aplicacion.com.interfaces.DistritoDao;
import aplicacion.com.interfaces.DniDao;
import aplicacion.com.interfaces.EmpleadoDao;
import aplicacion.com.interfaces.EstablecimientoDAO;
import aplicacion.com.interfaces.HojaDeEnvioDao;
import aplicacion.com.interfaces.MetodoPagoDAO;
import aplicacion.com.interfaces.MetodoRecojoDAO;
import aplicacion.com.interfaces.PedidoDAO;
import aplicacion.com.interfaces.ProductoDao;
import aplicacion.com.interfaces.ReclamosDao;
import aplicacion.com.interfaces.UsuarioDao;
import aplicacion.com.interfaces.ZonaDeRepartoDao;
import aplicacion.com.interfaces.EstadosPedidoDAO;
import aplicacion.com.interfaces.PromocionDAO;

public abstract class DAOFactory {
	// Dependiendo de qué origen de dato usaremos.
	public static final int MYSQL = 1;
	public static final int SQLSERVER = 2;
	public static final int ORACLE = 3;

	// Métodos abstractos que devolverá una instancia al modelo
	public abstract UsuarioDao getUsuarioModelo();
	public abstract EmpleadoDao getEmpleadoModelo();
	public abstract CargoDao getCargoModelo();
	public abstract ProductoDao getProducto();
	public abstract CatProdDao getCatProd();
	public abstract DniDao getDni();
	public abstract EstablecimientoDAO getEstablecimiento();
	public abstract ClienteDAO getCliente();
	public abstract ComprobanteDePagoDao getCDP();
	public abstract PedidoDAO getPedido();
	public abstract PromocionDAO getPromocion();
	public abstract MetodoPagoDAO getMetPago();
	public abstract EstadosPedidoDAO getEstadoPedido();
	public abstract MetodoRecojoDAO getMetRecojo();
	public abstract HojaDeEnvioDao getHojaEnvio();
	public abstract ReclamosDao getReclamos();
	public abstract ZonaDeRepartoDao getZona();
	public abstract DistritoDao getDistrito();

	// Devuelve la instancia de la clase que se termina conectando con el modelo.
	public static DAOFactory getDaoFactory(int tipo) {
		switch (tipo) {
		case MYSQL:
			return new MySqlDAOFactory();
		case SQLSERVER:
			return null;
		case ORACLE:
			return null;
		default:
			return null;
		}
	}
}
