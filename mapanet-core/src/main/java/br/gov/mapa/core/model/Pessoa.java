package br.gov.mapa.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Audited
@Entity
@AuditTable(schema = "auditoria", value = "pessoa_aud")
@Data
public class Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome", nullable=false)
	private String nome;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="data_nascimento", nullable=false)
	private Date dataNascimento;
	
	@Column(name="rg", nullable=false)
	private String rg;
	
	@Column(name="emissor_rg", nullable=false)
	private String emissorRg;
	
	@Column(name="cpf", nullable=false, unique=true)
	private String cpf;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "telefone_fixo")
	private String telefoneFixo;
	
	@NotBlank(message = "{telefoneCelular.notblank}")
	@Column(name = "telefone_celular")
	private String telefoneCelular;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Usuario usuario;
}
