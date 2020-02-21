package br.gov.mapa.core.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;


import lombok.Data;

@Audited
@Entity
@AuditTable(schema = "auditoria", value = "endereco_aud")
@Data
public class Endereco implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name="cep")
	private String cep;
	
	@NotBlank
	@Column(name="logradouro")
	private String logradouro;
	
	@NotBlank
	@Column(name="bairro")
	private String bairro;
	
	@Column(name="numero")
	private String numero;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="municipio_id")
	private Municipio municipio;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="estado_id")
	private Estado estado;
}
