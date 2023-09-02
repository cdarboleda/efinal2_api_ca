package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter{

	private static final Logger LOG = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	@Autowired
	private JwtUtils jwtUtils;
	
	//Filtra el request
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = this.parseJwt(request);
			if(jwt!=null && this.jwtUtils.validateJwtToken(jwt)) {
				String nombre = this.jwtUtils.getUsernameFromJwtToken(jwt);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(nombre, null, new ArrayList<>());	;
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//Seteamos la autenticacion en la session
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}	
		} catch (Exception e) {
			LOG.error("No se pudo realizar la autenticación con el token enviado: {}", e.getMessage());
		}
		
		filterChain.doFilter(request, response);
	}
	
	//Extraer el token del request
	private String parseJwt(HttpServletRequest request) {
		String valorCompleto = request.getHeader("Authorization");
		if(StringUtils.hasText(valorCompleto)&&valorCompleto.startsWith("Bearer ")) {
			return valorCompleto.substring(7, valorCompleto.length());
		}
		return null;
	}

}