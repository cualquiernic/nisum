package com.nisum.services;

import java.util.List;

import com.nisums.dto.MensajeDTO;
import com.nisums.dto.UserDTO;

/**
 * Interface para declarar los metodos de User Service
 */
public interface UserService {
	
	/**
	 * 
	 * @param userDTO
	 * @return
	 */
	public MensajeDTO save(UserDTO userDTO);
	/**
	 * 
	 * @param userDTO
	 */
	public MensajeDTO update(UserDTO userDTO);
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public UserDTO getByEmail(String email);
	
	/**
	 * 
	 * @param userDTO
	 * @return
	 */
	public MensajeDTO delete(UserDTO userDTO) ;
	/**
	 * 
	 * @return
	 */
	public List<UserDTO> getAll();
		
	
		
	

}
