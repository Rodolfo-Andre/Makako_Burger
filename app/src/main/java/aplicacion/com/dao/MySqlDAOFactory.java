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
import aplicacion.com.model.MySqlCargoDAO;
import aplicacion.com.model.MySqlCategoriaProducto;
import aplicacion.com.model.MySqlClienteDao;
import aplicacion.com.model.MySqlComprobanteDePagoDAO;
import aplicacion.com.model.MySqlDniDAO;
import aplicacion.com.model.MySqlEmpleadoDAO;
import aplicacion.com.model.MySqlEstablecimientoDAO;
import aplicacion.com.model.MySqlEstadosPedidoDAO;
import aplicacion.com.model.MySqlHojaDeEnvioDAO;
import aplicacion.com.model.MySqlMetodoPagoDAO;
import aplicacion.com.model.MySqlMetodoRecojoDAO;
import aplicacion.com.model.MySqlPedidoDAO;
import aplicacion.com.model.MySqlProductoDAO;
import aplicacion.com.model.MySqlPromocionDAO;
import aplicacion.com.model.MySqlReclamoDAO;
import aplicacion.com.model.MySqlUsuarioDAO;
import aplicacion.com.model.MySqlZonaDeRepartoDAO;
import aplicacion.com.model.MysqlDistritoDAO;

public class MySqlDAOFactory extends DAOFactory{
	@Override
	public UsuarioDao getUsuarioModelo() {
		return new MySqlUsuarioDAO();
	}

	@Override
	public CargoDao getCargoModelo() {
		return new MySqlCargoDAO();
	}

	@Override
	public ProductoDao getProducto() {
		return new MySqlProductoDAO();
	}

	@Override
	public CatProdDao getCatProd() {
		return new MySqlCategoriaProducto();
	}

	@Override
	public DniDao getDni() {
		return new MySqlDniDAO();
	}

	@Override
	public EmpleadoDao getEmpleadoModelo() {
		return new MySqlEmpleadoDAO();
	}
	@Override
	public EstablecimientoDAO getEstablecimiento() {
		return new MySqlEstablecimientoDAO();
	}

	@Override
	public ClienteDAO getCliente() {
		return new MySqlClienteDao();
	}

	@Override
	public PromocionDAO getPromocion() {
		return new MySqlPromocionDAO();
	}

	@Override
	public ComprobanteDePagoDao getCDP() {
		return new MySqlComprobanteDePagoDAO();
	}

	@Override
	public PedidoDAO getPedido() {
		return new MySqlPedidoDAO();
	}

	@Override
	public MetodoPagoDAO getMetPago() {
		return new MySqlMetodoPagoDAO();
	}

	@Override
	public EstadosPedidoDAO getEstadoPedido() {
		return new MySqlEstadosPedidoDAO();
	}

	@Override
	public MetodoRecojoDAO getMetRecojo() {
		return new MySqlMetodoRecojoDAO();
	}

	@Override
	public HojaDeEnvioDao getHojaEnvio() {
		return new MySqlHojaDeEnvioDAO();
	}

	@Override
	public ZonaDeRepartoDao getZona() {
		return new MySqlZonaDeRepartoDAO();
	}

	@Override
	public ReclamosDao getReclamos() {
		return new MySqlReclamoDAO();
	}
	
	@Override
	public DistritoDao getDistrito() {
		return new MysqlDistritoDAO();
	}
}
