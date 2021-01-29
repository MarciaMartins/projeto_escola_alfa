package com.br.alfaEscola.controle.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "prova")
public class Prova {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String descricao;
	
	@JsonIgnore
	private Long gabaritoId;
	
	
	@JsonIgnore
	@OneToOne(mappedBy = "prova")
	private Gabarito gabarito;
	
	public Gabarito getGabarito() {
		return gabarito;
	}
	
	public void setGabarito(Gabarito gabarito) {
		this.gabarito = gabarito;
	}
		
	
	public Prova(Gabarito gabarito) {
		this.descricao=("Prova ");
	}
	
	

	public Prova() {
		
	}


	

	public void setGabaritoId(Long gabaritoId) {
		this.gabaritoId = gabaritoId;
	}
	
	public Long getGabaritoId() {
		return gabaritoId;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		Prova other = (Prova) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	
}
