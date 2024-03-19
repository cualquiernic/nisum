package com.nisum.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
/**
 * Clase para la tabla usuario
 * @author Cristian Altamirano Llanos
 */
@Entity
@Table(name = "usuario")
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8311401261593242328L;
	private String id;
	private String name;
	private String email;
	private String password;
	private Timestamp creationDate;
	private Timestamp modificationDate;
	private String tokenJWT;
	private List<Phone> phones;
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name = "creation_date", nullable = false)
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	@Column(name = "modification_date", nullable = false)
	public Timestamp getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(Timestamp modificationDate) {
		this.modificationDate = modificationDate;
	}
	@Column(name = "token_jwt", nullable = false)
	public String getTokenJWT() {
		return tokenJWT;
	}
	public void setTokenJWT(String tokenJWT) {
		this.tokenJWT = tokenJWT;
	}
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	

}
