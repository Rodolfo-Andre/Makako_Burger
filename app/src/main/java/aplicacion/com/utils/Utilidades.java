package aplicacion.com.utils;

public class Utilidades {
	public static int random(int min, int max) {		
		return (int) (Math.round(Math.random() * (max - min)) + min);
	}
}
