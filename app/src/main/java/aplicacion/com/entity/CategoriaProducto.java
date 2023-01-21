package aplicacion.com.entity;

public class CategoriaProducto {
	private int Id_CatProd;
	private String nombre_CatProd;
	
	public String getNombre_CatProd() {
		return nombre_CatProd;
	}
	
	public void setNombre_CatProd(String nombre_CatProd) {
		this.nombre_CatProd = nombre_CatProd;
	}
	
	public int getId_CatProd() {
		return Id_CatProd;
	}
	
	public void setId_CatProd(int id_CatProd) {
		Id_CatProd = id_CatProd;
	}
}
