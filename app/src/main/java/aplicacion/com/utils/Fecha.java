package aplicacion.com.utils;

import java.sql.Timestamp;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Fecha {
	public static SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public Date StringADate(String Fecha) {
		SimpleDateFormat formatoTexto = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaE = null;
		
		try {
			fechaE = formatoTexto.parse(Fecha);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fechaE;
	}

	public java.sql.Date convertDataToSqlDate(java.util.Date javaDate) {
		java.sql.Date sqlDate = null;
		
		if (javaDate != null) {
			sqlDate = new java.sql.Date(javaDate.getTime());
		}
		
		return sqlDate;
	}
	
	public java.sql.Timestamp convertDataToSqlTimeStamp(java.util.Date javaDate) {
		long time = System.currentTimeMillis();
		java.sql.Timestamp sqlDate = null;
		
		if (javaDate != null) {
			time = javaDate.getTime();
			sqlDate = new Timestamp(time);
		}
		
		return sqlDate;
	}
	
	public Date StringADatetime(String Fecha) {
		DateFormat outFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date fechaE = null;
		
		try {
			fechaE = outFormat.parse(Fecha);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fechaE;
	}
	
	public Date StringADatetimeLocal(String date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		Date fechaE = null;
		
		try {
			fechaE = (Date)formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fechaE;
	}
}
