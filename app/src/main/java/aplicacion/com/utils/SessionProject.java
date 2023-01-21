package aplicacion.com.utils;

import javax.servlet.http.*;

public class SessionProject {
	public void saveSessionString(HttpServletRequest reques,String key,Object value) {
		HttpSession sesion=reques.getSession();
		sesion.setAttribute(key, value);
	}

	public void saveSessionTimeOut(HttpServletRequest request, int time) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(time);
	}
	
	public void invalidateSession(HttpServletRequest request) {
		request.getSession().invalidate();
	}
}