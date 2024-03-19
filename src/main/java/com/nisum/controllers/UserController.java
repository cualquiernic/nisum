package com.nisum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.services.UserService;
import com.nisums.dto.MensajeDTO;
import com.nisums.dto.UserDTO;
/**
 * Controlador de usuario
 * @author Cristian Altamirano Llanos
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService us;
	@PostMapping("/create")
	public MensajeDTO save(@RequestBody UserDTO user) {
		return us.save(user);
	}
	@GetMapping("/getbyemail")
	public UserDTO getBydId(@RequestBody UserDTO user) {
		return us.getByEmail(user.getEmail());
	}
	
	@PutMapping("/update")
	public MensajeDTO update(@RequestBody UserDTO user) {
		return us.update(user);
	}
	

}
