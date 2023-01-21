package aplication.com.listeners;

import java.text.SimpleDateFormat;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

/**
 * Application Lifecycle Listener implementation class SessionListernes
 *
 */
@WebListener
public class SessionListernes implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public SessionListernes() {}

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
    	   System.out.println("=============================================");
           System.out.println("Iniciando la session");
           System.out.println("ID Session ... " + arg0.getSession().getId());
           System.out.println("Creado ... " + new SimpleDateFormat().format(arg0.getSession().getCreationTime()));
           System.out.println("Inactividad... " + arg0.getSession().getMaxInactiveInterval());
    }
	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
    	  System.out.println("=============================================");
          System.out.println("Dejando la session");
          System.out.println("Ultima Session " + new SimpleDateFormat().format(arg0.getSession().getCreationTime()));
    }
}
