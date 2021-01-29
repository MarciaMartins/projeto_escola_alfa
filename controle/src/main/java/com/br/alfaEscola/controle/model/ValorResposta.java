package com.br.alfaEscola.controle.model;

public enum ValorResposta {
	
	A("a"),
	B("b"),
	C("c"),
	D("d"),
	E("e");
	
	private String descricao;

	ValorResposta(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
