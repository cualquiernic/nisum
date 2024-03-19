package com.nisum.controllers;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.components.JWTComponent;
import com.nisums.dto.TokenDTO;
import com.nisums.dto.UserDTO;


/**
 * JWT Controller
 * @author Cristian Altamirano Llanos
 */

@RestController
@RequestMapping("/jwt")
public class JWTController {
	@Autowired
	private JWTComponent jwtc;
	
	@PostMapping("/generate")
	public TokenDTO generate(@RequestBody UserDTO udto) {
		return jwtc.generateToken(udto.getEmail(), udto.getPassword());
		
	}
	
	@GetMapping("/validate")
	public TokenDTO validate(@RequestBody UserDTO udto){
		return jwtc.validateToken(udto.getTokenJWT());
		
	}
	
}
