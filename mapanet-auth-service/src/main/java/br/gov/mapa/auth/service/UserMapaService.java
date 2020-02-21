package br.gov.mapa.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserMapaService {

	public static Authentication authenticated() {
		try {
			return (Authentication) SecurityContextHolder.getContext().getAuthentication();
		}
		catch (Exception e) {
			return null;
		}
	}
}
