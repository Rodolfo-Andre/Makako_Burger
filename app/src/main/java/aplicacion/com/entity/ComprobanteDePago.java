package aplicacion.com.entity;

import java.util.Date;

public class ComprobanteDePago {
	private int codComprobante,idEstablecimiento, codPedido;
	private Date fchEmitido;
	private double precTotPedido,precCostoEnvio;
	private String dni;
	
	public int getCodComprobante() {
		return codComprobante;
	}
	
	public void setCodComprobante(int codComprobante) {
		this.codComprobante = codComprobante;
	}
	
	public int getIdEstablecimiento() {
		return idEstablecimiento;
	}
	
	public void setIdEstablecimiento(int idEstablecimiento) {
		this.idEstablecimiento = idEstablecimiento;
	}
	
	public int getCodPedido() {
		return codPedido;
	}
	
	public void setCodPedido(int codPedido) {
		this.codPedido = codPedido;
	}
	
	public Date getFchEmitido() {
		return fchEmitido;
	}
	
	public void setFchEmitido(Date fchEmitido) {
		this.fchEmitido = fchEmitido;
	}
	
	public double getPrecTotPedido() {
		return precTotPedido;
	}
	
	public void setPrecTotPedido(double precTotPedido) {
		this.precTotPedido = precTotPedido;
	}
	
	public double getPrecCostoEnvio() {
		return precCostoEnvio;
	}
	
	public void setPrecCostoEnvio(double precCostoEnvio) {
		this.precCostoEnvio = precCostoEnvio;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
}
