package aplicacion.com.interfaces;

import java.util.*;
import aplicacion.com.entity.*;

public interface PromocionDAO {
	public int agregarPromocion(Promocion promo, DetallePromocion detalle);
	public int actualizarPromocion(Promocion promo);
	public int deletePromocion(int id);
	public int getLastIdCombo();	
	public int getLastIdDetalleCombo();
	
	//Listado
	public ArrayList<Promocion> findAllPromocion(Map<String, Object> filtros);
	public ArrayList<DetallePromocion> findAllDetallePromoByidPromo(int idPromo);
	public int modificarDetallePromocion(DetallePromocion detalle, int tipoOperacion);
}
