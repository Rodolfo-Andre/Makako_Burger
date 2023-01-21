package aplicacion.com.entity;

import java.util.Date;

public class Reclamo {
	private int numReclamo, idCliente,idPedido, idReclamo,idTipoReclamo;
	private Date fechaReclamo;
	private String dscReclamo,dniCliente, nombreCliente, apellidoCliente,tipoReclamo;
	
	public int getNumReclamo() {
		return numReclamo;
	}
	
	public void setNumReclamo(int numReclamo) {
		this.numReclamo = numReclamo;
	}
	
	public String getDniCliente() {
		return dniCliente;
	}
	
	public void setDniCliente(String dniCliente) {
		this.dniCliente = dniCliente;
	}
	
	public Date getFechaReclamo() {
		return fechaReclamo;
	}
	
	public void setFechaReclamo(Date fechaReclamo) {
		this.fechaReclamo = fechaReclamo;
	}

	public int getIdTipoReclamo() {
		return idTipoReclamo;
	}
	
	public void setIdTipoReclamo(int idTipoReclamo) {
		this.idTipoReclamo = idTipoReclamo;
	}
	
	public String getTipoReclamo() {
		return tipoReclamo;
	}
	
	public void setTipoReclamo(String tipoReclamo) {
		this.tipoReclamo = tipoReclamo;
	}
	
	public String getDscReclamo() {
		return dscReclamo;
	}
	
	public void setDscReclamo(String dscReclamo) {
		this.dscReclamo = dscReclamo;
	}
	
	public String getApellidoCliente() {
		return apellidoCliente;
	}
	
	public void setApellidoCliente(String apellidoCliente) {
		this.apellidoCliente = apellidoCliente;
	}
	
	public String getNombreCliente() {
		return nombreCliente;
	}
	
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	
	public int getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	public int getIdPedido() {
		return idPedido;
	}
	
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	
	public int getIdReclamo() {
		return idReclamo;
	}
	
	public void setIdReclamo(int idReclamo) {
		this.idReclamo = idReclamo;
	}	
}
