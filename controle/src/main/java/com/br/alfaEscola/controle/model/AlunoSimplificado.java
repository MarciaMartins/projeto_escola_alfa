package com.br.alfaEscola.controle.model;

public class AlunoSimplificado {

	private Long id;
	private String nome;
	private int notaFinal;
	private String status;
	
	
	public AlunoSimplificado() {
		
	}
	
	
	public int getNotaFinal() {
		return notaFinal;
	}


	public void setNotaFinal(int notaFinal) {
		this.notaFinal = notaFinal;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
		AlunoSimplificado other = (AlunoSimplificado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
