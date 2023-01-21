package aplicacion.com.entity;

public class Establecimiento {
	public int idEstablecimiento;
	public int idZonaReparto;
	public String nomEstablecimiento;
	public String telefonoEstablecimiento;
	public String SobreNosotros;
	public Distrito Distri;
	public ZonaDeReparto Zona;
	
	public int getIdZonaReparto() {
		return idZonaReparto;
	}

	public void setIdZonaReparto(int idZonaReparto) {
		this.idZonaReparto = idZonaReparto;
	}

	public Distrito getDistri() {
		return Distri;
	}

	public void setDistri(Distrito distri) {
		Distri = distri;
	}

	public ZonaDeReparto getZona() {
		return Zona;
	}

	public void setZona(ZonaDeReparto zona) {
		Zona = zona;
	}

	public int getIdEstablecimiento() {
		return idEstablecimiento;
	}

	public void setIdEstablecimiento(int idEstablecimiento) {
		this.idEstablecimiento = idEstablecimiento;
	}

	public String getNomEstablecimiento() {
		return nomEstablecimiento;
	}

	public void setNomEstablecimiento(String nomEstablecimiento) {
		this.nomEstablecimiento = nomEstablecimiento;
	}

	public String getTelefonoEstablecimiento() {
		return telefonoEstablecimiento;
	}

	public void setTelefonoEstablecimiento(String telefonoEstablecimiento) {
		this.telefonoEstablecimiento = telefonoEstablecimiento;
	}

	public String getSobreNosotros() {
		return SobreNosotros;
	}

	public void setSobreNosotros(String sobreNosotros) {
		SobreNosotros = sobreNosotros;
	}
}
