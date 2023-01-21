package aplicacion.com.interfaces;

import java.util.*;
import aplicacion.com.entity.Producto;

public interface ProductoDao {
	public int agregarProducto(Producto p);
	public int updateProducto(Producto p);
	public int deleteProducto(int codPro);
	public int getLastId();
	public ArrayList<Producto> listProd(Map<String, Object> filtros);
}
