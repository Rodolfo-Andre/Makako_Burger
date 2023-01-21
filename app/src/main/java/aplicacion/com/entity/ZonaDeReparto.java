package aplicacion.com.entity;

public class ZonaDeReparto {
	private int IdZonareparto;
	private String nombreZona;
	private int idDistrito;
	private String latitud;
	private String longitud;
	private Distrito distri;
	
	public Distrito getDistri() {
		return distri;
	}

	public void setDistri(Distrito distri) {
		this.distri = distri;
	}

	public int getIdZonareparto() {
		return IdZonareparto;
	}

	public void setIdZonareparto(int idZonareparto) {
		IdZonareparto = idZonareparto;
	}
	
	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	public int getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(int idDistrito) {
		this.idDistrito = idDistrito;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
}
