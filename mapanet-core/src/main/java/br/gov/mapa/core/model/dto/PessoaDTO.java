package br.gov.mapa.core.model.dto;

import java.util.Date;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PessoaDTO {
	
	public PessoaDTO() { }
	
	public PessoaDTO(String nome, String cpf, String rg, Date dataNascimento, String email) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.rg = rg;
		this.dataNascimento = dataNascimento;
		this.email = email;
	}
	
	private String nome;
	
	@CPF
	//@CpfExistente
	//@UsuarioExistente
	private String cpf;
	private String rg;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;
	
	@Email
	private String email;
}
