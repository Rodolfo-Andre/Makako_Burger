/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.65
 * Generated at: 2023-01-21 03:01:08 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class indexUser_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("/snippets/headerIndexUser.jsp", Long.valueOf(1674190882641L));
    _jspx_dependants.put("/snippets/footerIndexUser.jsp", Long.valueOf(1674190266543L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1672774626605L));
    _jspx_dependants.put("jar:file:/C:/Users/Rodolfo%20Andre/Desktop/Test_2/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/app/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1668791496000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fchoose = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fotherwise = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fchoose.release();
    _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
    _005fjspx_005ftagPool_005fc_005fotherwise.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"es\">\r\n");
      out.write("<head>\r\n");
      out.write("	<meta charset=\"UTF-8\">\r\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("	<link rel=\"icon\" type=\"image/png\" href=\"./img/icono.jpg\" sizes=\"32x32\"/>\r\n");
      out.write("	<title>Makako Burger</title>\r\n");
      out.write("	\r\n");
      out.write("	<!--FONT AWESOME-->\r\n");
      out.write("	<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css\" integrity=\"sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==\" crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\" />\r\n");
      out.write("	\r\n");
      out.write("	<!--GOOGLE FONTS - RALEWAY-->\r\n");
      out.write("	<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\r\n");
      out.write("	<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\r\n");
      out.write("	<link href=\"https://fonts.googleapis.com/css2?family=Chau+Philomene+One&family=Raleway:wght@500&family=Secular+One&display=swap\" rel=\"stylesheet\">\r\n");
      out.write("	\r\n");
      out.write("	<!--BOOTSTRAP CSS-->\r\n");
      out.write("	<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD\" crossorigin=\"anonymous\">\r\n");
      out.write("\r\n");
      out.write("	<!--CUSTOM CSS-->\r\n");
      out.write("	<link rel=\"stylesheet\" href=\"./css/user_style.css\">\r\n");
      out.write("	\r\n");
      out.write("	<!--SCRIPTS-->\r\n");
      out.write("	<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"d-flex flex-column min-vh-100\">\r\n");
      out.write("	");
      out.write("<header class=\"text-center shadow-lg mb-4\" style=\"background-color: #FABE1F;\">\r\n");
      out.write("	<video src=\"./img/titulo.mp4\" style=\"width: 100%; max-width: 500px;\" autoplay loop muted=\"muted\"></video>\r\n");
      out.write("		\r\n");
      out.write("	<nav class=\"navbar navbar-expand-xl\" style=\"background-color: #FFF6EA;\">\r\n");
      out.write("		<div class=\"container-fluid\">\r\n");
      out.write("			<a class=\"navbar-brand\" style=\"color: #FFF6EA;\" href=\"indexUser.jsp\">\r\n");
      out.write("				<img src=\"./img/logo_fondo_transparente.png\" width=\"90\" alt=\"logo\">\r\n");
      out.write("			</a>\r\n");
      out.write("\r\n");
      out.write("			<button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" style=\"color: #330E00;\" data-bs-target=\"#navbarNav\" aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\r\n");
      out.write("				<i class=\"fa-solid fa-bars\"></i>\r\n");
      out.write("			</button>\r\n");
      out.write("				\r\n");
      out.write("			<div class=\"collapse navbar-collapse justify-content-end\" id=\"navbarNav\">\r\n");
      out.write("				<ul class=\"navbar-nav gap-2 align-items-center\" style=\"color: #FFF6EA;\">\r\n");
      out.write("					<li class=\"nav-item\">\r\n");
      out.write("						<a class=\"nav-link\" style=\"color: #330E00; font-size: 20px;\" aria-current=\"page\" href=\"indexUser.jsp\">\r\n");
      out.write("							<i class=\"fa-solid fa-house-chimney d-flex justify-content-center\"></i>\r\n");
      out.write("							Inicio\r\n");
      out.write("						</a>\r\n");
      out.write("					</li>\r\n");
      out.write("\r\n");
      out.write("					<li class=\"nav-item\">\r\n");
      out.write("						<a class=\"nav-link\" style=\"color: #330E00; font-size: 20px;\" aria-current=\"page\" href=\"menuUsuario.jsp\">\r\n");
      out.write("							<i class=\"fa-solid fa-burger d-flex justify-content-center\"></i>\r\n");
      out.write("							Menú\r\n");
      out.write("						</a>\r\n");
      out.write("					</li>\r\n");
      out.write("\r\n");
      out.write("					<li class=\"nav-item\">\r\n");
      out.write("						<a class=\"nav-link\" style=\"color: #330E00; font-size: 20px;\" aria-current=\"page\" href=\"PedidosController?type=ListarPromocion\">\r\n");
      out.write("							<i class=\"fa-solid fa-sack-dollar d-flex justify-content-center\"></i>\r\n");
      out.write("							Promociones\r\n");
      out.write("						</a>\r\n");
      out.write("					</li>\r\n");
      out.write("\r\n");
      out.write("					<li class=\"nav-item\">\r\n");
      out.write("						<a class=\"nav-link\" style=\"color: #330E00; font-size: 20px;\" aria-current=\"page\" href=\"Zona_de_reparto.jsp\"> \r\n");
      out.write("							<i class=\"fa-solid fa-map-location-dot d-flex justify-content-center\"></i>\r\n");
      out.write("							Zona de Reparto\r\n");
      out.write("						</a>\r\n");
      out.write("					</li>\r\n");
      out.write("\r\n");
      out.write("					<li class=\"nav-item\">\r\n");
      out.write("						<a class=\"nav-link\" style=\"color: #330E00; font-size: 20px;\" aria-current=\"page\" href=\"SobreNosotrosController?type=listarinfo\"> \r\n");
      out.write("							<i class=\"fa-solid fa-people-roof d-flex justify-content-center\"></i>\r\n");
      out.write("							Nosotros\r\n");
      out.write("						</a>\r\n");
      out.write("					</li>\r\n");
      out.write("\r\n");
      out.write("					<li class=\"nav-item d-flex align-self-center btn-item\">\r\n");
      out.write("						<a class=\"nav-link p-3\" style=\"color: #330E00; font-size: 20px;\" aria-current=\"page\" href=\"PedidosController?type=carritoCompra\"> \r\n");
      out.write("							<i class=\"fa-solid fa-shopping-cart justify-content-center pe-2\"></i> \r\n");
      out.write("							Tienda Online\r\n");
      out.write("						</a>\r\n");
      out.write("					</li>\r\n");
      out.write("\r\n");
      out.write("					");
      if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("				</ul>\r\n");
      out.write("			</div>\r\n");
      out.write("		</div>\r\n");
      out.write("	</nav>\r\n");
      out.write("</header>");
      out.write("\r\n");
      out.write("	\r\n");
      out.write("	<main class=\"flex-grow-1 d-flex align-items-center\">\r\n");
      out.write("		<div class=\"container-fluid\">\r\n");
      out.write("			<div id=\"myCarousel\" class=\"carousel slide\" >\r\n");
      out.write("				<div class=\"carousel-indicators\">\r\n");
      out.write("					<button type=\"button\" data-bs-target=\"#myCarousel\" data-bs-slide-to=\"0\" class=\"active\" aria-current=\"true\" aria-label=\"Slide 1\"></button>\r\n");
      out.write("					<button type=\"button\" data-bs-target=\"#myCarousel\" data-bs-slide-to=\"1\" aria-label=\"Slide 2\"></button>\r\n");
      out.write("					<button type=\"button\" data-bs-target=\"#myCarousel\" data-bs-slide-to=\"2\" aria-label=\"Slide 3\"></button>\r\n");
      out.write("				</div>\r\n");
      out.write("				\r\n");
      out.write("				<div class=\"carousel-inner\">\r\n");
      out.write("					<div class=\"carousel-item active h-100\">\r\n");
      out.write("						<img src=\"./img/banner_1.jpg\">\r\n");
      out.write("					</div>\r\n");
      out.write("					\r\n");
      out.write("					<div class=\"carousel-item h-100\">\r\n");
      out.write("						<video src=\"./img/banner_2.mp4\" autoplay loop muted></video>\r\n");
      out.write("					</div>\r\n");
      out.write("					\r\n");
      out.write("					<div class=\"carousel-item h-100\">\r\n");
      out.write("						<video src=\"./img/banner_3.mp4\" autoplay loop muted></video>\r\n");
      out.write("					</div>\r\n");
      out.write("				</div>\r\n");
      out.write("			</div>\r\n");
      out.write("\r\n");
      out.write("			<div class=\"border m-4 mx-auto rounded-3 d-flex flex-wrap border-1\" style=\"max-width: 600px\">\r\n");
      out.write("				<div class=\"p-3 border-end border-1 d-flex justify-content-center flex-grow-1\" style=\"background-color: #FFF6EA;\">\r\n");
      out.write("					<img src=\"./img/makako_burger.png\" class=\"img-fluid\" alt=\"Makako Burger\" style=\"width: 100%; max-width: 200px;\">\r\n");
      out.write("				</div>\r\n");
      out.write("\r\n");
      out.write("				<div class=\"p-3 d-flex flex-column justify-content-center flex-grow-1\">\r\n");
      out.write("					<div>\r\n");
      out.write("						<h2 class=\"text-center makako-font text-uppercase\">Makako Social</h2>\r\n");
      out.write("\r\n");
      out.write("						<div class=\"d-flex flex-column gap-4\">\r\n");
      out.write("							<div class=\"text-start \">\r\n");
      out.write("								<a href=\"https://www.facebook.com/profile.php?id=100063526752640\" target=\"_blank\" class=\"text-decoration-none text-white bg-primary p-2 rounded-2 border-0\" style=\"letter-spacing: 1.5px\">\r\n");
      out.write("									<i class=\"fa-brands fa-facebook p-1\"></i>\r\n");
      out.write("									Facebook\r\n");
      out.write("								</a>						\r\n");
      out.write("							</div>\r\n");
      out.write("\r\n");
      out.write("							<div class=\"text-center\">\r\n");
      out.write("								<a href=\"https://www.instagram.com/makakoburgercafe/\" target=\"_blank\" class=\"text-decoration-none text-white p-2 rounded-2 border-0\" style=\"letter-spacing: 1.5px; background: linear-gradient(25deg, #405de6, #5851db, #833ab4, #c13584, #e1306c, #fd1d1d);\">\r\n");
      out.write("									<i class=\"fa-brands fa-instagram\"></i> \r\n");
      out.write("									Instagram\r\n");
      out.write("								</a>\r\n");
      out.write("							</div>\r\n");
      out.write("\r\n");
      out.write("							<div class=\"text-end\">\r\n");
      out.write("								<a href=\"https://api.whatsapp.com/send?phone=51966313252\" target=\"_blank\" class=\"text-decoration-none text-white p-2 rounded-2 border-0\" style=\"letter-spacing: 1.5px; background-color: #4DC95B;\">\r\n");
      out.write("									<i class=\"fa-brands fa-whatsapp p-1\"></i>\r\n");
      out.write("									WhatsApp\r\n");
      out.write("								</a>\r\n");
      out.write("							</div>\r\n");
      out.write("						</div>\r\n");
      out.write("					</div>\r\n");
      out.write("				</div>\r\n");
      out.write("			</div>\r\n");
      out.write("		</div>\r\n");
      out.write("	</main>\r\n");
      out.write("	\r\n");
      out.write("	");
      out.write("<footer class=\"text-white\" style=\"background-color: #492311; border-top: 2px solid #FABE1F;\">\r\n");
      out.write("	<div class=\"d-flex flex-wrap justify-content-evenly mt-3 gap-2\">\r\n");
      out.write("		<div class=\"d-flex flex-column text-center\">\r\n");
      out.write("			<h5>Libro de Reclamaciones</h5>\r\n");
      out.write("			\r\n");
      out.write("			<a href=\"ReclamosController?type=obtenerInfor\">\r\n");
      out.write("				<img src=\"./img/libro_reclamaciones.png\" alt=\"\" style=\"width: 40px;\">\r\n");
      out.write("			</a>\r\n");
      out.write("\r\n");
      out.write("			<ul class=\"list-unstyled\">\r\n");
      out.write("				<li>Terminos y Condiciones</li>\r\n");
      out.write("				<li>Políticas de la Empresa</li>\r\n");
      out.write("				<li>Mapa de Sitio</li>\r\n");
      out.write("				<li>Política de Datos Personales</li>\r\n");
      out.write("			</ul>\r\n");
      out.write("		</div>\r\n");
      out.write("		\r\n");
      out.write("		<div class=\"d-flex flex-column text-center\">\r\n");
      out.write("			<h5>Horarios de Atencion</h5>\r\n");
      out.write("\r\n");
      out.write("			<ul class=\"list-unstyled\">\r\n");
      out.write("				<li>Lunes a Viernes:</li>\r\n");
      out.write("				<li>10:00 a.m. - 12:00 p.m.</li>\r\n");
      out.write("				<li>Fines de Semana:</li>\r\n");
      out.write("				<li>11:00 a.m. - 7:00 p.m.</li>\r\n");
      out.write("				<li>Feriados: Tienda Cerrada</li>\r\n");
      out.write("			</ul>\r\n");
      out.write("		</div>\r\n");
      out.write("	</div>\r\n");
      out.write("\r\n");
      out.write("	<div class=\"container p-0 d-flex flex-wrap border-top border-2 justify-content-center justify-content-lg-between\">\r\n");
      out.write("		<div class=\"d-flex flex-wrap align-items-center justify-content-center\">\r\n");
      out.write("			<h6 class=\"pe-2 mb-0\">Métodos de Pago</h6>\r\n");
      out.write("			\r\n");
      out.write("			<div class=\"fs-3\" style=\"letter-spacing: 5px;\">\r\n");
      out.write("				<i class=\"fa-brands fa-cc-mastercard text-danger\"></i> \r\n");
      out.write("				<i class=\"fa-brands fa-cc-visa text-warning\"></i>\r\n");
      out.write("				<i class=\"fa-brands fa-cc-amex text-primary\"></i>\r\n");
      out.write("			</div>\r\n");
      out.write("			\r\n");
      out.write("			<div class=\"border-start ms-3 mb-0 ps-3 d-flex align-items-center\">\r\n");
      out.write("				<h6 class=\"mb-0 me-2\">Contacto</h6>\r\n");
      out.write("				\r\n");
      out.write("				<a href=\"https://api.whatsapp.com/send?phone=51966313252\" target=\"_blank\" class=\"text-decoration-none text-white fw-normal\">\r\n");
      out.write("					+51 966 313 252 \r\n");
      out.write("					<i class=\"fa-brands fa-whatsapp\"></i>\r\n");
      out.write("				</a>\r\n");
      out.write("			</div>\r\n");
      out.write("		</div>\r\n");
      out.write("		\r\n");
      out.write("		<div class=\"d-flex align-items-center\">\r\n");
      out.write("			<p class=\"m-0 fw-light\">© 2022 Todos los derechos reservados</p>\r\n");
      out.write("		</div>\r\n");
      out.write("	</div>\r\n");
      out.write("</footer>");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fchoose_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:choose
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag) _005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
    boolean _jspx_th_c_005fchoose_005f0_reused = false;
    try {
      _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fchoose_005f0.setParent(null);
      int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
      if (_jspx_eval_c_005fchoose_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("						");
          if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
            return true;
          out.write("\r\n");
          out.write("						\r\n");
          out.write("						");
          if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
            return true;
          out.write("\r\n");
          out.write("					");
          int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fchoose_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
      _jspx_th_c_005fchoose_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fchoose_005f0, _jsp_getInstanceManager(), _jspx_th_c_005fchoose_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f0 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    boolean _jspx_th_c_005fwhen_005f0_reused = false;
    try {
      _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fwhen_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f0);
      // /snippets/headerIndexUser.jsp(59,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fwhen_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ nomUsuario != null }", boolean.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null)).booleanValue());
      int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
      if (_jspx_eval_c_005fwhen_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("							<div class=\"nav-item dropdown d-flex align-self-center btn-item\" style=\"font-size: 20px;\">\r\n");
          out.write("								<a href=\"#\" class=\"d-flex align-items-center p-2 link-dark text-decoration-none dropdown-toggle user-photo\" id=\"dropdownUser3\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\"> \r\n");
          out.write("									<img src=\"./img/default_user.png\" alt=\"photo\" style=\"width: 40px; height: 40px; border-radius: 50%;\">\r\n");
          out.write("									<span class=\"m-2\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ empleado != null ? empleado.getNom_Empleado() : cliente.getNomCliente() }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
          out.write("</span>\r\n");
          out.write("								</a>\r\n");
          out.write("\r\n");
          out.write("								<ul class=\"dropdown-menu dropdown-menu-end p-0\" style=\"color: #330E00; font-size: 20px; background-color: #FABE1F; position: absolute;\">\r\n");
          out.write("									");
          if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
            return true;
          out.write("\r\n");
          out.write("\r\n");
          out.write("									<li>\r\n");
          out.write("										<hr class=\"dropdown-divider m-0\">\r\n");
          out.write("									</li>\r\n");
          out.write("										\r\n");
          out.write("									<li>\r\n");
          out.write("										<a href=\"login?type=logout\" class=\"dropdown-item fw-bold py-2\">Cerrar Sesión</a>\r\n");
          out.write("									</li>\r\n");
          out.write("								</ul>\r\n");
          out.write("							</div>\r\n");
          out.write("						");
          int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fwhen_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
      _jspx_th_c_005fwhen_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fwhen_005f0, _jsp_getInstanceManager(), _jspx_th_c_005fwhen_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fchoose_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fwhen_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:choose
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f1 = (org.apache.taglibs.standard.tag.common.core.ChooseTag) _005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
    boolean _jspx_th_c_005fchoose_005f1_reused = false;
    try {
      _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
      _jspx_th_c_005fchoose_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fwhen_005f0);
      int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
      if (_jspx_eval_c_005fchoose_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("										");
          if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
            return true;
          out.write("\r\n");
          out.write("										\r\n");
          out.write("										");
          if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
            return true;
          out.write("\r\n");
          out.write("									");
          int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fchoose_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
      _jspx_th_c_005fchoose_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fchoose_005f1, _jsp_getInstanceManager(), _jspx_th_c_005fchoose_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f1, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f1 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    boolean _jspx_th_c_005fwhen_005f1_reused = false;
    try {
      _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
      _jspx_th_c_005fwhen_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f1);
      // /snippets/headerIndexUser.jsp(68,10) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fwhen_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ nomUsuario.getIdTipoUsuario() == 1 }", boolean.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null)).booleanValue());
      int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
      if (_jspx_eval_c_005fwhen_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("											<li>\r\n");
          out.write("												<a href=\"indexAdmin.jsp\" class=\"dropdown-item fw-bold py-2\">Configuración</a>\r\n");
          out.write("											</li>\r\n");
          out.write("										");
          int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fwhen_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
      _jspx_th_c_005fwhen_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fwhen_005f1, _jsp_getInstanceManager(), _jspx_th_c_005fwhen_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fotherwise_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f1, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:otherwise
    org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag) _005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
    boolean _jspx_th_c_005fotherwise_005f0_reused = false;
    try {
      _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fotherwise_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f1);
      int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
      if (_jspx_eval_c_005fotherwise_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("											<li>\r\n");
          out.write("												<a href=\"CajaController?type=listarCDPCli\" class=\"dropdown-item fw-bold py-2\">Ver mis CDP</a>\r\n");
          out.write("											</li>\r\n");
          out.write("										");
          int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fotherwise_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
      _jspx_th_c_005fotherwise_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fotherwise_005f0, _jsp_getInstanceManager(), _jspx_th_c_005fotherwise_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fotherwise_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:otherwise
    org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag) _005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
    boolean _jspx_th_c_005fotherwise_005f1_reused = false;
    try {
      _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
      _jspx_th_c_005fotherwise_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f0);
      int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
      if (_jspx_eval_c_005fotherwise_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("							<li class=\"nav-item d-flex align-self-center btn-item\">\r\n");
          out.write("								<a class=\"nav-link p-3\" style=\"color: #330E00; font-size: 20px;\" aria-current=\"page\" href=\"login.jsp\">\r\n");
          out.write("									<i class=\"fa-solid fa-user justify-content-center pe-2\"></i>\r\n");
          out.write("									Iniciar Sesión\r\n");
          out.write("								</a>\r\n");
          out.write("							</li>\r\n");
          out.write("						");
          int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fotherwise_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
      _jspx_th_c_005fotherwise_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fotherwise_005f1, _jsp_getInstanceManager(), _jspx_th_c_005fotherwise_005f1_reused);
    }
    return false;
  }
}
