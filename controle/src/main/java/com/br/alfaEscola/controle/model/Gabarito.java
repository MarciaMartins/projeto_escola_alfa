package com.br.alfaEscola.controle.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "gabarito")
public class Gabarito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String descricao;
	
	@OneToMany(mappedBy = "gabarito")
	private List<Resposta> respostas;
	
	@OneToOne
	@JoinColumn(name = "prova_id")
	private Prova prova;
	
	@JsonIgnore
	@NotNull
	private int quantidadeRespostas;
	
	public Gabarito(Long id, List<Resposta> respostas) {
		this.id = id;
		this.respostas = respostas;
	}
	
	public Gabarito() {
		
	}
	
	
	public void setQuantidadePerguntas(int quantidadePerguntas) {
		this.quantidadeRespostas = quantidadePerguntas;
	}
	
	public int getQuantidadeRespostas() {
		return quantidadeRespostas;
	}
	
	public Prova getProva() {
		return prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
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
		Gabarito other = (Gabarito) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Gabarito [id=" + id + ", descricao=" + descricao + ", respostas=" + respostas + "]";
	}
	
	
	
	
	
	
	
}