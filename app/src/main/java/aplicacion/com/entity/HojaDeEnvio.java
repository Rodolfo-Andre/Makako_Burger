package aplicacion.com.entity;

public class HojaDeEnvio {
	private int codHojaEnvio;
	private int codPedido;
	private int codZonaReparto;
	private String telefono;
	private int codEstadoPedido;
	private ZonaDeReparto zona;
	private EstadosPedido Estados;
	
	public EstadosPedido getEstados() {
		return Estados;
	}
	
	public void setEstados(EstadosPedido estados) {
		Estados = estados;
	}
	
	public int getCodHojaEnvio() {
		return codHojaEnvio;
	}
	
	public void setCodHojaEnvio(int codHojaEnvio) {
		this.codHojaEnvio = codHojaEnvio;
	}
	
	public int getCodPedido() {
		return codPedido;
	}
	
	public void setCodPedido(int codPedido) {
		this.codPedido = codPedido;
	}
	
	public int getCodZonaReparto() {
		return codZonaReparto;
	}
	
	public void setCodZonaReparto(int codZonaReparto) {
		this.codZonaReparto = codZonaReparto;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public int getCodEstadoPedido() {
		return codEstadoPedido;
	}
	
	public void setCodEstadoPedido(int codEstadoPedido) {
		this.codEstadoPedido = codEstadoPedido;
	}
	
	public ZonaDeReparto getZona() {
		return zona;
	}
	
	public void setZona(ZonaDeReparto zona) {
		this.zona = zona;
	}
}

