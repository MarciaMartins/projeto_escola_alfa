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

@Entity
@Table(name = "prova_aluno")
public class ProvaAluno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "aluno_id")
	private Aluno aluno;
	
	@OneToOne
	@JoinColumn(name = "prova_id")
	private Prova prova;
	
	@OneToMany(mappedBy = "provaAluno")
	private List<RespostaAluno> respostasAluno;
	
	//@JsonIgnore
	private int notaProva;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public Prova getProva() {
		return prova;
	}
	public void setProva(Prova prova) {
		this.prova = prova;
	}
	public List<RespostaAluno> getRespostasAluno() {
		return respostasAluno;
	}
	public void setRespostasAluno(List<RespostaAluno> respostasAluno) {
		this.respostasAluno = respostasAluno;
	}
	
	
	public void setNotaProva(int notaProva) {
		this.notaProva = notaProva;
	}
	
	public int getNotaProva() {
		return notaProva;
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
		ProvaAluno other = (ProvaAluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProvaAluno [id=" + id + ", prova=" + prova + "]";
	}
	
	
	
	
	
	
}
