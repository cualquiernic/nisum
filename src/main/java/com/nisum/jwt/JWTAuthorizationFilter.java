package com.nisum.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nisum.utils.Constantes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
/**
 * Clase implementacion JWT
 * @author Cristian Altamirano Llanos
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	
	/**
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			if (existeJWTToken(request, response)) {
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
					SecurityContextHolder.clearContext();
			}
			chain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}	
	/**
	 * Metodo para validar token
	 * @param request
	 * @return
	 */
	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(Constantes.HEADER_TOKEN).replace(Constantes.PREFIX_TOKEN, "");
		return Jwts.parser().setSigningKey(Constantes.SECRET_TOKEN.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	/**
	 * Metodo para autenticarse dentro del flujo de Spring
	 * 
	 * @param claims
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setUpSpringAuthentication(Claims claims) {
		List<String> authorities = (List) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}
	/**
	 * Metodo para validar si el token existe en el header del payload
	 * @param request
	 * @param res
	 * @return
	 */
	private boolean existeJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(Constantes.HEADER_TOKEN);
		if (authenticationHeader == null || !authenticationHeader.startsWith(Constantes.PREFIX_TOKEN))
			return false;
		return true;
	}

}
