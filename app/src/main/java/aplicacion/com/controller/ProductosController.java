package aplicacion.com.controller;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.google.gson.*;
import aplicacion.com.dao.*;
import aplicacion.com.entity.*;
import aplicacion.com.interfaces.*;

/**
 * Servlet implementation class ProductosController
 */
@WebServlet("/ProductosController")
@MultipartConfig()
public class ProductosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductosController() {
		super();
	}

	private DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
	private CatProdDao categoriaProdDao = daoFactory.getCatProd();
	private ProductoDao productoDao = daoFactory.getProducto();
	private PromocionDAO promocionDao = daoFactory.getPromocion();
	private PedidoDAO pedidoDAO = daoFactory.getPedido();
	private ArrayList<CategoriaProducto> listarCatProd = null;
	private ArrayList<Producto> listarProd = null;

	private PrintWriter pw;
	private Gson gson = new Gson();
	
	// Ruta de la imagen
	private String rutaProd = "";

	// Página
	private int pag = 1;
	private int nroDeRegistroXPag = 5;
	private int maxDePaginacion = 5;
	private int totalDePaginacion = 0;
	private int comienza = 0;
	private int termina = 0;
	private int cantTotalDeRegistro = 0;

	// Variables de búsqueda
	private String busqueda = "";

	// Filtros de búsqueda
	private String categoriaProductooB = "";
	private String precioInicio = "";
	private String precioFinal = "";

	@Override
	public void init() throws ServletException {
		rutaProd = getServletContext().getRealPath("") + "img/productos";
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String tipo = request.getParameter("type");
		
		// Obtiene lo del JS
		pw = response.getWriter();
		
		if (tipo != null) {
			switch (tipo) {
				case "addInfoObject": {
					anadirInfoObjeto(request, response);
					break;
				}
				case "getInfoObject": {
					obtenerInfoObjeto(request, response);
					break;
				}
				case "updateInfoObject": {
					actualizarObjeto(request, response);
					break;
				}
				case "deleteInfoObject": {
					eliminarObjeto(request, response);
					break;
				}
				case "getListCategoryProd": {
					obtenerListaCategoriaProducto(request, response);
					break;
				}
				case "findProductInPromotionDetailsOrOrders": {
					buscarProductoEnDetallesPromocionOPedidos(request, response);
					break;
				}
				case "getProdByCategory": {
					obtenerProductosPorCategoria(request, response);
					break;
				}
				default: {
					response.sendRedirect("ProductosController?pProductos=" + pag);
					break;
				}
			}
		} else {
			listarInfo(request, response);
		}
	}

	private void obtenerProductosPorCategoria(HttpServletRequest request, HttpServletResponse response) {
		String idCategoria = request.getParameter("id");
		List<Producto> lista = null;
		Map<String, Object> filtrosProd = new HashMap<>()
				;
		filtrosProd.put("Id_CatProd", idCategoria);
		
		lista = productoDao.listProd(filtrosProd);
		String jsObject = gson.toJson(lista);
		pw.print(jsObject);
	}

	private void buscarProductoEnDetallesPromocionOPedidos(HttpServletRequest request, HttpServletResponse response) {
		int idProducto = Integer.parseInt(request.getParameter("id"));

		ArrayList<DetallePromocion> detallePromocion = promocionDao.findAllDetallePromoByidPromo(0);
		
		long encontroProductoEnPromocion = detallePromocion.stream().filter(dp -> dp.getCodPro() == idProducto).count();
		boolean encontroProductoEnPedido = false;
		
		if (encontroProductoEnPromocion == 0) {
			encontroProductoEnPedido = pedidoDAO.findAllDetallePedidoByCodPedido(0).stream().anyMatch(pd -> pd.getCodProd() == idProducto);
		}
		
		String llave = encontroProductoEnPromocion != 0 ? "foundProductInPromotion" : "foundProductInOrders";
		Object valor = encontroProductoEnPromocion != 0 ? encontroProductoEnPromocion : encontroProductoEnPedido;

		JsonObject jsObject = new JsonObject();			
		
		if (valor instanceof Long) {			
			jsObject.addProperty(llave, Long.parseLong(valor.toString()));
		} else {
			jsObject.addProperty(llave, Boolean.parseBoolean(valor.toString()));
		}

		pw.print(jsObject);
	}

	private void eliminarObjeto(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		String nombreArchivo = listarProd.stream().filter(p -> p.getCodProd() == id).findFirst().get().getImagenProd();
		
		File borrarImagen = new File(rutaProd + "/" + nombreArchivo + ".png");
		Files.deleteIfExists(borrarImagen.toPath());
		productoDao.deleteProducto(id);
		
		response.sendRedirect("ProductosController?pProductos=" + pag);
	}

	private void actualizarObjeto(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int codPro = Integer.parseInt(request.getParameter("id"));
		String nomPro = request.getParameter("nameProd");
		int idCatProd = Integer.parseInt(request.getParameter("cboCatUpdate"));
		double precioPro = Double.parseDouble(request.getParameter("priceProd"));

		// Validar que la imagen no sea la misma de antes
		String nombreArchivo = listarProd.stream().filter(p -> p.getCodProd() == codPro).findFirst().get()
				.getImagenProd();
		
		// Si la imagen es nula, no la actualiza
		boolean imagenEsNoNulo = false;
		
		// Manejar imagen
		Part imagen = request.getPart("image");
		
		// Es para validar que la imagen que está llegando del modal es mayor a 0, es
		// decir, que se ingreso una imagen
		// Si es que se ingreso, la cambia, si es que no, usa la anterior
		if (imagenEsNoNulo = imagen.getSize() > 0) {
			File validaCarpeta = new File(rutaProd);
			
			// Si es que la carpeta no existe, entonces la crea
			if (!validaCarpeta.exists()) validaCarpeta.mkdir();
			
			InputStream imagenBytes = imagen.getInputStream();
			File archivoImagen = new File(validaCarpeta.getAbsolutePath() + "/" + nombreArchivo + ".png");
			Files.copy(imagenBytes, archivoImagen.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}

		Producto prod = new Producto();
		prod.setCodProd(codPro);
		prod.setId_CatProd(idCatProd);
		prod.setNomPro(nomPro);
		prod.setPrecioPro(precioPro);
		
		// Si es que la imagen es verdadera, entonces la reemplaza, si es que no, no
		// hace nada.
		if (imagenEsNoNulo) prod.setImagenProd(nombreArchivo);
		
		productoDao.updateProducto(prod);
		response.sendRedirect("ProductosController?pProductos=" + pag);
	}

	private void obtenerInfoObjeto(HttpServletRequest request, HttpServletResponse response) {
		int idProd = Integer.parseInt(request.getParameter("id"));
		
		// De la lista que se obtiene por defecto, se hace una búsqueda con el stream,
		// el filtro es crear un objeto que recorre el arreglo, es como un forEach
		// Encontrar la primera coincidencia mediante el id, si es que no, devuelve
		// nulo.
		Producto prod = listarProd.stream().filter(p -> p.getCodProd() == idProd).findFirst().orElse(null);
		
		// Validar si lo encontro
		if (prod != null) {
			String prodJsonString = gson.toJson(prod);
			pw.print(prodJsonString);
		}
	}

	private void obtenerListaCategoriaProducto(HttpServletRequest request, HttpServletResponse response) {
		listarCatProd = categoriaProdDao.listarCategoriaProd();
		String catProdJsonString = gson.toJson(listarCatProd);
		pw.print(catProdJsonString);
	}

	private void anadirInfoObjeto(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int codPro = productoDao.getLastId() + 1;
		String nomPro = request.getParameter("nameProd");
		int idCatProd = Integer.parseInt(request.getParameter("cboCat"));
		double precioPro = Double.parseDouble(request.getParameter("priceProd"));
		
		// Obtener la imagen del objeto part
		Part imagen = request.getPart("image");
		
		// Obtener imagen en flujo de bytes
		InputStream imagenBytes = imagen.getInputStream();

		// Validamos la carpeta, sino exite la carpeta, entonces la creamos //La ruta
		// está en init() del inicio del Servlet
		// Dicha ruta no se muestra en el proyecto, es carpeta creada por el servidor.
		File validaCarpeta = new File(rutaProd);
		if (!validaCarpeta.exists()) validaCarpeta.mkdir();

		// Crear copia de imagen y almacenar en la ruta especificada
		String nombreArchivo = String.valueOf(new Date().getTime());
		File archivoImagen = new File(validaCarpeta.getAbsolutePath() + "/" + nombreArchivo + ".png");
		Files.copy(imagenBytes, archivoImagen.toPath(), StandardCopyOption.REPLACE_EXISTING);

		Producto prod = new Producto();
		prod.setCodProd(codPro);
		prod.setId_CatProd(idCatProd);
		prod.setNomPro(nomPro);
		prod.setPrecioPro(precioPro);
		prod.setImagenProd(nombreArchivo);

		System.out.println(getServletContext().getRealPath("") + "img/productos");
		productoDao.agregarProducto(prod);
		response.sendRedirect("ProductosController?pProductos=" + pag);
	}

	private void listarInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*----------------------------- Variables de búsqueda -----------------------------*/
		// Validamos la cantidad de páginas o registros de productos
		String validaPagProductos = request.getParameter("pProductos");
		
		// Indicar
		int pagPeticionProducto = 0;

		// Aquí valida si el producto ingresa por su nombre es valido
		String busquedaProductoValida = request.getParameter("searchProd");
		
		// Valida la categoría recibida y los parametros de búsqueda de productos
		String validaCategoriaProdB = request.getParameter("cboCategoryProd");
		String validaPrecioInicio = request.getParameter("startPrice");
		String validaPrecioFinal = request.getParameter("endPrice");

		// Aquí depende de los filtros, ya que se busca por precio de inicio o final,
		// pero no es necesario ambos.
		Map<String, Object> filtrosProd = new HashMap<>();

		/*----------------------------- Validación de Búsqueda de Plato -----------------------------*/
		// Si se ha ingresado un producto en el filtro de búsqueda, lo agrega a la
		// variable, si es que no, lista todo
		if (busquedaProductoValida != null) {
			busqueda = busquedaProductoValida;
			validaPagProductos = "1";
		}
		
		// Agrega al diccionario el filtro de búsqueda
		if (!busqueda.isBlank()) {
			filtrosProd.put("busqueda", busqueda);
		}

		// Validar las categoría de productos seleccionadas
		// categoriaProductoB es una variable global, el valida es el que recibe y lo
		// agrega para buscar mediante dicha variable
		if (validaCategoriaProdB != null) {
			if (validaCategoriaProdB.equals("all")) categoriaProductooB = "";
			else categoriaProductooB = validaCategoriaProdB;
		}

		// Si la categoría tiene valor, entonces se le da al filtro de búsqueda
		if (!categoriaProductooB.isBlank()) filtrosProd.put("Id_CatProd", categoriaProductooB);

		// Lo mismo, se dan valores de precio
		precioInicio = validaPrecioInicio != null ? validaPrecioInicio : precioInicio;
		if (!precioInicio.isBlank()) filtrosProd.put("precioInicio", precioInicio);

		precioFinal = validaPrecioFinal != null ? validaPrecioFinal : precioFinal;
		if (!precioFinal.isBlank()) filtrosProd.put("precioFinal", precioFinal);

		/*----------------------------- Listando productos -----------------------------*/
		// Variable para ir calculando que sea par
		double mitadMaximoPaginacion = Math.round(maxDePaginacion / 2.0);
		
		// Aquí saco los valores que se van a mostrar, las categorías en el comboBox
		listarCatProd = categoriaProdDao.listarCategoriaProd();
		
		// Los productos mediante los filtros.
		listarProd = productoDao.listProd(filtrosProd);

		// Esto de aquí es para calcular cuantos registros se van a mostrar
		cantTotalDeRegistro = listarProd.size();
		
		// Si es que son 6 registros en el list, entonces se va convirtiendo a un número
		// que sea aceptable, que sería 5, logrando que se divida en 2 de páginación
		totalDePaginacion = cantTotalDeRegistro % nroDeRegistroXPag == 0 ? cantTotalDeRegistro / nroDeRegistroXPag
				: (cantTotalDeRegistro / nroDeRegistroXPag) + 1;

		// Validar lo de las páginas que se van a mostrar y que siempre sea de 0 a 9
		if (validaPagProductos != null && validaPagProductos.matches("^[0-9]+$")
				&& (pagPeticionProducto = Integer.parseInt(validaPagProductos)) <= totalDePaginacion
				&& pagPeticionProducto > 0) {
			pag = pagPeticionProducto;
		}

		if (cantTotalDeRegistro > 0) {
			int desde = (nroDeRegistroXPag * pag) - nroDeRegistroXPag;
			int hasta = (nroDeRegistroXPag * pag) > cantTotalDeRegistro ? cantTotalDeRegistro : nroDeRegistroXPag * pag;

			// Va sacando de la lista según sea necesario los valores para calcular cuánto
			// debe listar.
			List<Producto> listaUsuarioTemporal = listarProd.subList(desde, hasta);
			
			// Si devuelve 0 o es menor a 0, comenzará con 1
			if (pagPeticionProducto - mitadMaximoPaginacion <= 0) {
				comienza = 1;
			} else {
				// Si es que no es menor a 0, entonces calculará la resta para ver de dónde
				// comienza
				comienza = (int) (pagPeticionProducto - mitadMaximoPaginacion);
			}

			if (comienza + maxDePaginacion - 1 > totalDePaginacion) {
				termina = totalDePaginacion;
				comienza = termina - maxDePaginacion + 1 <= 0 ? 1 : termina - maxDePaginacion + 1;
			} else {
				termina = comienza + maxDePaginacion - 1;
			}

			request.setAttribute("comienza", comienza);
			request.setAttribute("termina", termina);
			request.setAttribute("listProductos", listaUsuarioTemporal);
		}

		request.setAttribute("pag", pag);
		request.setAttribute("totalDePaginacion", totalDePaginacion);
		request.setAttribute("totalRegistro", cantTotalDeRegistro);
		request.setAttribute("listaCategoriaProductoP", listarCatProd);
		request.setAttribute("busqueda", busqueda);
		request.setAttribute("categoriaProdB", categoriaProductooB);
		request.setAttribute("precioInicio", precioInicio);
		request.setAttribute("precioFinal", precioFinal);
		request.getRequestDispatcher("productos.jsp").forward(request, response);
	}
}
