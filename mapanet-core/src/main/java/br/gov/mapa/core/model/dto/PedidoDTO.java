package br.gov.mapa.core.model.dto;

import lombok.Data;

@Data
public class PedidoDTO {

	private String token;
	private String cpf;
	private String email;
	private String nome;
	private String url;
	private String senha; 
	private String template;
}
