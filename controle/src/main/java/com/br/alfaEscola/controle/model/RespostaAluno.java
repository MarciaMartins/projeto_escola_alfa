package com.br.alfaEscola.controle.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name ="resposta_aluno")
public class RespostaAluno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private ValorResposta resposta;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "provaAluno_id")
	private ProvaAluno provaAluno;
		

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ValorResposta getResposta() {
		return resposta;
	}

	public void setResposta(ValorResposta resposta) {
		this.resposta = resposta;
	}

	public ProvaAluno getProvaAluno() {
		return provaAluno;
	}

	public void setProvaAluno(ProvaAluno provaAluno) {
		this.provaAluno = provaAluno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RespostaAluno other = (RespostaAluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
