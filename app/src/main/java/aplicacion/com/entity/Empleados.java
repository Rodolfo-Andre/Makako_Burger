package aplicacion.com.entity;

import java.util.Date;
import aplicacion.com.utils.Utilidades;

public class Empleados {
	private int IdEmpleado;
	private String nom_Empleado;
	private String ape_Empleado;
	private Date fechaRegistro;
	private String telfEmpleado;
	private String DNI_Empleado;
	private Cargo cargo;
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getIdEmpleado() {
		return IdEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		IdEmpleado = idEmpleado;
	}

	public String getNom_Empleado() {
		return nom_Empleado;
	}

	public void setNom_Empleado(String nom_Empleado) {
		this.nom_Empleado = nom_Empleado;
	}

	public String getApe_Empleado() {
		return ape_Empleado;
	}

	public void setApe_Empleado(String ape_Empleado) {
		this.ape_Empleado = ape_Empleado;
	}

	public String getTelfEmpleado() {
		return telfEmpleado;
	}

	public void setTelfEmpleado(String telfEmpleado) {
		this.telfEmpleado = telfEmpleado;
	}

	public String getDNI_Empleado() {
		return DNI_Empleado;
	}

	public void setDNI_Empleado(String dNI_Empleado) {
		DNI_Empleado = dNI_Empleado;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String generarContraseña() {
		int nroCaracterExtraer = 2;
		int nroRamdom = Utilidades.random(1, ape_Empleado.length() - nroCaracterExtraer);
		String caracterApe = ape_Empleado.substring(nroRamdom, nroRamdom + nroCaracterExtraer);
		String mayusculaCaracterApe = caracterApe.substring(0, 1).toUpperCase() + caracterApe.substring(1);

		return mayusculaCaracterApe + "$" + Utilidades.random(1000, 5000);
	}
}
