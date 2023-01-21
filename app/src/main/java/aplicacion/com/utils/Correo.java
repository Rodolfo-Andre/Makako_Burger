package aplicacion.com.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Correo implements Runnable {
	private String correo;
	private String mensaje;
	private String asunto;
	
	public Correo(String correo, String mensaje, String asunto) {
		this.correo = correo;
		this.mensaje = mensaje;
		this.asunto = asunto;
	}
	
	public void enviarMensaje(String correo, String mensaje, String asunto) {
		// Creamos una instancia del objeto Properties utilizando la librer�a javax.mail v1.6.2 y activation
		Properties propiedades = System.getProperties();
		
		// Establecemos las propiedades
		// smtp -> Protocolo para transferencia simple de correo
		// En este caso utilizaremos el protocolo de gmail
		propiedades.setProperty("mail.smtp.host", "smtp.office365.com");  // El servidor SMTP a conectarse es gmail
		propiedades.setProperty("mail.smtp.starttls.enable", "true"); // Habilitamos para una conexi�n protegida por TLS
		propiedades.setProperty("mail.smtp.port", "587"); // Utilizamos el puerto 587, que es para el env�o de emails
		propiedades.setProperty("mail.smtp.auth", "true"); // Si es verdadero, intenta autenticar al usuario mediante el comando AUTH. El valor predeterminado es falso.

		String correoEmisor = ""; // Debes de colocar tu correo
		String contrase�a = ""; // Debes de colocar tu contrase�a
		
		// Sessi�n 
		Session session = Session.getDefaultInstance(propiedades, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication (correoEmisor, contrase�a);
		    }
		});

		// Para establecer los mensajes, asuntos, etc
		/*
		 * Esta clase representa un mensaje de correo electr�nico de estilo MIME.
		 * Los clientes que deseen crear nuevos mensajes de estilo MIME crear�n una instancia de un objeto MimeMessage vac�o y luego lo completar�n con los atributos y el contenido adecuados.
		 * */
		MimeMessage mail = new MimeMessage(session); // Utilizamos el constructor por defecto

		try {
			// InternetAddress -> Esta clase representa una direcci�n de correo electr�nico de Internet utilizando la sintaxis de RFC822.
			mail.setFrom(new InternetAddress(correoEmisor)); // Establecemos el remitente
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(correo)); // Establecemos los destinatarios y el tipo TO -> Los destinatarios "Para" (principales)
			mail.setSubject(asunto); // Establecemos el campo de encabezado "Asunto"
			mail.setText(mensaje); // Establecemos el mensaje en texto plano
			
			// El transporte
			// Transport -> Una clase abstracta que modela un transporte de mensajes.
			//Transport transporte = sesion.getTransport("smtp"); // Obtiene un transporte mediante el protocolo smtp
			//transporte.connect(correoEnvia, contrasena); // Connectando al host actual utilizando el nombre de usuario y la contrase�a especificados.
			//transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));  // Env�a el mensaje a la lista de direcciones especificada.
			//transporte.close();
			Transport.send(mail, mail.getRecipients(Message.RecipientType.TO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		enviarMensaje(correo, mensaje, asunto);
	}
}