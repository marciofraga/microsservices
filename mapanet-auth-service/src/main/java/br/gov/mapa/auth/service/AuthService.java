package br.gov.mapa.auth.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.gov.mapa.auth.exceptions.ObjectNotFoundException;
import br.gov.mapa.core.model.Pessoa;
import br.gov.mapa.core.model.Usuario;
import br.gov.mapa.core.model.dto.PedidoDTO;
import br.gov.mapa.core.repository.PessoaRepository;
import br.gov.mapa.core.repository.UsuarioRepository;
import br.gov.mapa.core.services.EmailService;

@Service
public class AuthService {

	@Autowired(required = true)
	private EmailService emailService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryp;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findByEmail(email);
		
		if (!pessoa.isPresent())
			throw new ObjectNotFoundException("Email não encontrado");
		
		emailService.sendOrderConfirmationHtmlEmail(geraPedidoSenha(pessoa.get()), "Nova senha");
	}
	
	private PedidoDTO geraPedidoSenha(Pessoa pessoa) {
		PedidoDTO pedido = new PedidoDTO();
		pedido.setCpf(pessoa.getCpf());
		pedido.setEmail(pessoa.getEmail());
		pedido.setNome(pessoa.getNome());
		pedido.setSenha(geraSenha(pessoa));
		pedido.setUrl("http://localhost:8080/autentica");
		pedido.setTemplate("email/senha");
		return pedido;
	}
	
	private String geraSenha(Pessoa pessoa) {
		Usuario usuario = pessoa.getUsuario();
		String novaSenha = newPassword();
		
		usuario.setPassword(bcryp.encode(novaSenha));
		usuarioRepository.save(usuario);
		return novaSenha;
	}
	
	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
