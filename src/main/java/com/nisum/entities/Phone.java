package com.nisum.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
/**
 * Clase para la tabla telefono
 * @author Cristian Altamirano LLanos
 */
@Entity
@Table(name = "phone")
@SequenceGenerator(name = "seq_phone", sequenceName = "seq_phone", allocationSize = 1)
public class Phone implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4956824925735325165L;
	private Long id;
	private String number;
	private String cityCode;
	private String countryCode;
	private User user;
	@Id
	@GeneratedValue(generator = "seq_phone", strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "number", nullable = true)
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Column(name = "city_code", nullable = true)
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	@Column(name = "country_code", nullable = true)
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@ManyToOne
	@JoinColumn(name = "id_user", nullable = true)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
