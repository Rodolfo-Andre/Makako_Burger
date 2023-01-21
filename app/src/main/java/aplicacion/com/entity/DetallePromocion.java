package aplicacion.com.entity;

public class DetallePromocion {
	private int codDetallePromo;
	private int codPromo;
	private int codPro;
	private int cantidad;
	private Producto prod;
	
	public Producto getProd() {
		return prod;
	}
	
	public void setProd(Producto prod) {
		this.prod = prod;
	}
	
	public int getCodDetallePromo() {
		return codDetallePromo;
	}
	
	public void setCodDetallePromo(int codDetallePromo) {
		this.codDetallePromo = codDetallePromo;
	}
	
	public int getCodPromo() {
		return codPromo;
	}
	
	public void setCodPromo(int codPromo) {
		this.codPromo = codPromo;
	}
	
	public int getCodPro() {
		return codPro;
	}
	
	public void setCodPro(int codPro) {
		this.codPro = codPro;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
