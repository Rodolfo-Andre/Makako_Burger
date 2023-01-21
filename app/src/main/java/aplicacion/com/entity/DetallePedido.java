package aplicacion.com.entity;

public class DetallePedido {
	private int numDetalle;
	private int codPedido;
	private int codProd;
	private int codPromo;
	private int cant;
	private double precioPedidoTot;
	
	//Detalle adicionales
	private Producto product;
	private Promocion promo;
	
	public Producto getProduct() {
		return product;
	}
	
	public void setProduct(Producto product) {
		this.product = product;
	}
	
	public Promocion getPromo() {
		return promo;
	}
	
	public void setPromo(Promocion promo) {
		this.promo = promo;
	}
	
	public int getCodPromo() {
		return codPromo;
	}
	
	public void setCodPromo(int codPromo) {
		this.codPromo = codPromo;
	}
	
	public int getCant() {
		return cant;
	}
	
	public void setCant(int cant) {
		this.cant = cant;
	}
	
	public double getPrecioPedidoTot() {
		return precioPedidoTot;
	}
	
	public void setPrecioPedidoTot(double precioPedidoTot) {
		this.precioPedidoTot = precioPedidoTot;
	}
	
	public int getCodProd() {
		return codProd;
	}
	
	public void setCodProd(int codProd) {
		this.codProd = codProd;
	}

	public int getNumDetalle() {
		return numDetalle;
	}
	
	public void setNumDetalle(int numDetalle) {
		this.numDetalle = numDetalle;
	}
	
	public int getCodPedido() {
		return codPedido;
	}
	
	public void setCodPedido(int codPedido) {
		this.codPedido = codPedido;
	}
}
