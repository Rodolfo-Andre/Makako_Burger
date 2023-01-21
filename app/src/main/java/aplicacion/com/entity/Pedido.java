package aplicacion.com.entity;

import java.util.Date;

public class Pedido {
	private int codPedido;
	private Date fechaPedido;
	private int idCliente;
	private double precTotPedido;
	private int codEstadoPedido;
	private int codMetodoRecojo;
	private int codMetPago;
	
	//Adicionales
	private MetodoRecojo Metodo;
	private EstadosPedido Estados;
	private Cliente Cliente;
	private MetodoPago MetodoPago;
	
	public MetodoPago getMetodoPago() {
		return MetodoPago;
	}
	
	public void setMetodoPago(MetodoPago metodoPago) {
		MetodoPago = metodoPago;
	}
	
	public int getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public MetodoRecojo getMetodo() {
		return Metodo;
	}
	
	public void setMetodo(MetodoRecojo metodo) {
		Metodo = metodo;
	}
	
	public EstadosPedido getEstados() {
		return Estados;
	}
	
	public void setEstados(EstadosPedido estados) {
		Estados = estados;
	}
	
	public Cliente getCliente() {
		return Cliente;
	}
	
	public void setCliente(Cliente cliente) {
		Cliente = cliente;
	}
	
	public int getCodPedido() {
		return codPedido;
	}
	
	public void setCodPedido(int codPedido) {
		this.codPedido = codPedido;
	}
	
	public Date getFechaPedido() {
		return fechaPedido;
	}
	
	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	
	public double getPrecTotPedido() {
		return precTotPedido;
	}
	
	public void setPrecTotPedido(double precTotPedido) {
		this.precTotPedido = precTotPedido;
	}
	
	public int getCodEstadoPedido() {
		return codEstadoPedido;
	}
	
	public void setCodEstadoPedido(int codEstadoPedido) {
		this.codEstadoPedido = codEstadoPedido;
	}
	
	public int getCodMetodoRecojo() {
		return codMetodoRecojo;
	}
	
	public void setCodMetodoRecojo(int codMetodoRecojo) {
		this.codMetodoRecojo = codMetodoRecojo;
	}
	
	public int getCodMetPago() {
		return codMetPago;
	}
	
	public void setCodMetPago(int codMetPago) {
		this.codMetPago = codMetPago;
	}
}
