package com.nisums.dto;

/**
 * 
 */
public class MensajeDTO {
	
	private String estado;
	private String mensaje;
	private String tokenJWT;
	
	public MensajeDTO() {
		
	}
	public MensajeDTO(String estado, String mensaje) {
		this.estado = estado;
		this.mensaje = mensaje;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getTokenJWT() {
		return tokenJWT;
	}
	public void setTokenJWT(String tokenJWT) {
		this.tokenJWT = tokenJWT;
	}
	
	
}
