package aplicacion.com.entity;

import java.util.Date;

public class Promocion {
	private int codPromo;
	private double precioPromo;
	private String nomPromo;
	private Date fechaVigencia;
	private String imagenCombo;
	
	public String getNomPromo() {
		return nomPromo;
	}
	
	public void setNomPromo(String nomPromo) {
		this.nomPromo = nomPromo;
	}
	
	public int getCodPromo() {
		return codPromo;
	}
	
	public void setCodPromo(int codPromo) {
		this.codPromo = codPromo;
	}
	
	public double getPrecioPromo() {
		return precioPromo;
	}
	
	public void setPrecioPromo(double precioPromo) {
		this.precioPromo = precioPromo;
	}
	
	public Date getFechaVigencia() {
		return fechaVigencia;
	}
	
	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}
	
	public String getImagenCombo() {
		return imagenCombo;
	}
	
	public void setImagenCombo(String imagenCombo) {
		this.imagenCombo = imagenCombo;
	}
}
