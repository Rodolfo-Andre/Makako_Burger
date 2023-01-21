package aplicacion.com.entity;

import java.util.Date;

public class ReporteCDP {
	private int codPedido;
	private Date fechaEmision;
	private String nomMetRecojo;
	private String nomMetPago;
	private String dniCli;
	private double precioPedidoTot;
	
	//Detalles
	private String nom;
	private int cant;
	private double precioPro;
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public Date getFechaEmision() {
		return fechaEmision;
	}
	
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	
	public double getPrecioPedidoTot() {
		return precioPedidoTot;
	}
	
	public void setPrecioPedidoTot(double precioPedidoTot) {
		this.precioPedidoTot = precioPedidoTot;
	}
	
	public int getCodPedido() {
		return codPedido;
	}
	
	public void setCodPedido(int codPedido) {
		this.codPedido = codPedido;
	}
	
	public String getNomMetRecojo() {
		return nomMetRecojo;
	}
	
	public void setNomMetRecojo(String nomMetRecojo) {
		this.nomMetRecojo = nomMetRecojo;
	}
	
	public String getNomMetPago() {
		return nomMetPago;
	}
	
	public void setNomMetPago(String nomMetPago) {
		this.nomMetPago = nomMetPago;
	}
	
	public String getDniCli() {
		return dniCli;
	}
	
	public void setDniCli(String dniCli) {
		this.dniCli = dniCli;
	}

	public int getCant() {
		return cant;
	}
	
	public void setCant(int cant) {
		this.cant = cant;
	}
	
	public double getPrecioPro() {
		return precioPro;
	}
	
	public void setPrecioPro(double precioPro) {
		this.precioPro = precioPro;
	}
}
