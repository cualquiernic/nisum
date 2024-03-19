package com.nisums.dto;

import java.util.List;

/**
 * Clase Data Transfer Object para UserDTO
 * @author Cristian Alutamirano LLanos
 */
public class UserDTO {
	
	private String id;
	private String name;
	private String email;
	private String password;
	private String creationDate;
	private String modificationDate;
	private String tokenJWT;
	private List<PhoneDTO> phones;
	
	public UserDTO() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}
	
	public String getTokenJWT() {
		return tokenJWT;
	}
	public void setTokenJWT(String tokenJWT) {
		this.tokenJWT = tokenJWT;
	}
	
	
	public List<PhoneDTO> getPhones() {
		return phones;
	}
	public void setPhones(List<PhoneDTO> phones) {
		this.phones = phones;
	}
	
	

}
