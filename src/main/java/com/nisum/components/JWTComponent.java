package com.nisum.components;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.stereotype.Component;

import com.nisum.entities.User;
import com.nisum.repositories.UserRepository;
import com.nisum.utils.Constantes;
import com.nisums.dto.TokenDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Clase componente que tiene funciones del token awt
 * @author Cristian Altamirano Llanos
 */
@Component
public class JWTComponent {
    private static final Logger logger = LogManager.getLogger(JWTComponent.class);	
	@Autowired
	private UserRepository ur;
	/**
	 * funcion para crear token cuando se crean el usuario
	 * @param login
	 * @return
	 */
	public String generateTokenOnCreateUser(String login) {
		try {
			return getJWTToken(login);
		}catch(Exception e) {
			logger.error("No se pudo generar el token", e);
			return null;
		}
	}
	/**
	 * 
	 * @param login
	 * @param clave
	 * @return
	 */
	public TokenDTO generateToken(String email, String clave) {
		TokenDTO tokenDTO = new TokenDTO();
		try {
			
			User u = ur.getByEmail(email);
			if (u == null) {
				tokenDTO.setDescripcion("Usuario y password no coinciden");
				tokenDTO.setToken("");
				tokenDTO.setEstado(Constantes.RESPUESTA_REST_FAIL);
				return tokenDTO;
			}
			String claveDB = u.getPassword();
			if (!claveDB.equals(clave)) {
				tokenDTO.setDescripcion("Usuario y password no coinciden");
				tokenDTO.setToken("");
				tokenDTO.setEstado(Constantes.RESPUESTA_REST_FAIL);

				return tokenDTO;
			}
			String token = getJWTToken(u.getEmail());
			tokenDTO.setToken(token);	
			tokenDTO.setDescripcion("Token Generado exitisamente");
			tokenDTO.setEstado(Constantes.RESPUESTA_REST_OK);
		}catch(Exception e) {
			tokenDTO.setDescripcion("Usuario y password no coinciden");
			tokenDTO.setToken("");
			tokenDTO.setEstado(Constantes.RESPUESTA_REST_FAIL);
			logger.error("No se pudo generar el token", e);
		}
		return tokenDTO;

		
	}
	public TokenDTO validateToken(String authToken) {
		TokenDTO tdto = new TokenDTO();
		try {
				validateClaimsToken(authToken);
				tdto.setDescripcion("Token valido");
		        tdto.setEstado(Constantes.RESPUESTA_REST_OK);		
		    } catch (UnsupportedJwtException | MalformedJwtException e) {
				tdto.setDescripcion("Token Invalido");
		        tdto.setEstado(Constantes.RESPUESTA_REST_FAIL);
		        logger.error("Token invalido", e);
		    }
			catch (ExpiredJwtException e) {
		    	tdto.setDescripcion("Token Expirado");
		        tdto.setEstado(Constantes.RESPUESTA_REST_FAIL);
		        logger.error("Token Expirado", e);
		    } 
		return tdto;
	}
	
	private Claims validateClaimsToken(String jwtToken) {
		return Jwts.parser().setSigningKey(Constantes.SECRET_TOKEN.getBytes(Charset.forName("UTF-8"))).parseClaimsJws(jwtToken).getBody();
	}	
	
	/**
	 * 
	 * @param login
	 * @return
	 */
	private String getJWTToken(String login) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		Date fechaCreacion = new Date(System.currentTimeMillis());
		Date fechaExpiracion = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60));

		String token = Jwts
				.builder()
				.setId("amarisJWT")
				.setSubject(login)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(fechaCreacion)
				.setExpiration(fechaExpiracion)
				.signWith(SignatureAlgorithm.HS512,
						Constantes.SECRET_TOKEN.getBytes(Charset.forName("UTF-8"))).compact();

		return token;
	}

}
