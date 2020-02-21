package br.gov.mapa.auth.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.gov.mapa.core.model.Permissao;
import br.gov.mapa.core.model.Usuario;
import br.gov.mapa.core.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserMapaDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByUsername(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("usuario não encontrado");
		}

		Collection<GrantedAuthority> auths = new ArrayList<>();

		for (Permissao permissao : usuario.getPermissoes()) {
			auths.add(new SimpleGrantedAuthority(permissao.getNome()));
		}

		return new User(usuario.getUsername(), usuario.getPassword(), auths);
	}
}
