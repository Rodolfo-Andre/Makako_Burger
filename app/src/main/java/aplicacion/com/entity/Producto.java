package aplicacion.com.entity;

public class Producto {
	private int codProd;
	private String nomPro;
	private double precioPro;
	private int Id_CatProd;
	private String imagenProd;
	private CategoriaProducto catProducto;
	
	public CategoriaProducto getCatProducto() {
		return catProducto;
	}
	
	public void setCatProducto(CategoriaProducto catProducto) {
		this.catProducto = catProducto;
	}
	
	public int getCodProd() {
		return codProd;
	}
	
	public void setCodProd(int codProd) {
		this.codProd = codProd;
	}
	
	public String getNomPro() {
		return nomPro;
	}
	
	public void setNomPro(String nomPro) {
		this.nomPro = nomPro;
	}
	
	public double getPrecioPro() {
		return precioPro;
	}
	
	public void setPrecioPro(double precioPro) {
		this.precioPro = precioPro;
	}
	
	public int getId_CatProd() {
		return Id_CatProd;
	}
	
	public void setId_CatProd(int id_CatProd) {
		Id_CatProd = id_CatProd;
	}
	
	public String getImagenProd() {
		return imagenProd;
	}
	
	public void setImagenProd(String imagenProd) {
		this.imagenProd = imagenProd;
	}
}
