package com.nisum.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilidades {
	/**
	 * Constructor privado
	 */
	private Utilidades() {
		
	}
	/**
	 * Metodo para obtener la fecha y hora actual
	 * @return Timestamp
	 */
	public static Timestamp getCurrenDate(){
    	Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());              
        return new Timestamp(cal.getTime().getTime());
    }
	
	/**
	 * Metodo para convertir un timestamp a fecha y hora en formato dd-MM-yyyy HH:mm:ss
	 * @param fechaInicio
	 * @return
	 */
	public static String tsToFechaDDMMAAA(Timestamp fecha) {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fecha);
	}
	/***
	 * 
	 * @param fecha
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp strToTsDDMMAAA(String fecha) throws ParseException{
		return new Timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(fecha).getTime());
	}
	/**
	 * 
	 * @param email
	 * @return
	 */
	public static Boolean validarEmail(String email){
		Pattern pattern = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	/**
	 * Validar formato de contraseña 1 May, min y dos digitos
	 * @param clave
	 * @return
	 */
	public static Boolean validarContrasena(String clave) {
		String regex = "^"
                + "(?=.*[A-Z])"
                + "(?=.*[a-z])"
                + "(?=.*\\d{2})"
                + "(?=\\S+$).{1,255}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(clave);
		return matcher.matches();
	}
	
	/**
	 * Metodo para generar un id unico
	 * @return String
	 */
	public static String generateUniqueId() {
	    int limiteDerecho = 97; // desde la letra a
	    int limiteIzquierdo = 122; // hasta la letra z
	    int largo = 12;
	    Random random = new Random();
	    int numeroEntreUnoDiez = random.nextInt(10) + 1;
	    String generatedString = random.ints(limiteDerecho, limiteIzquierdo + 1)
	      .limit(largo)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    return numeroEntreUnoDiez + generatedString.toUpperCase();
	}

}
