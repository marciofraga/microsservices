package br.gov.mapa.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.mapa.core.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findByUsername(String username);
	Usuario findByEmail(String email);
}
