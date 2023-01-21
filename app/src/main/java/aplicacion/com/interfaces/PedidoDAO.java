package aplicacion.com.interfaces;

import java.util.*;
import aplicacion.com.entity.*;

public interface PedidoDAO {
	public int actualizarPedido(int estado, int codPedido);
	public ArrayList<Pedido> findAll(Map<String, Object> filtros);
	public ArrayList<DetallePedido> findAllDetallePedidoByCodPedido(int codPedido);
}
