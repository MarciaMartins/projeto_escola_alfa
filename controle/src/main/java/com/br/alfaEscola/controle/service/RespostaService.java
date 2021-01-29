package com.br.alfaEscola.controle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.alfaEscola.controle.model.Resposta;
import com.br.alfaEscola.controle.repository.RespostaRepository;
import com.br.alfaEscola.controle.service.exception.ValorNaoPadronizado;

@Service
public class RespostaService {

	@Autowired
	private RespostaRepository respostaRespository;
	
	public Resposta validarPesoQuestao(Resposta resposta) {
		
		if(resposta.getPesoQuestao() < 0) {
			throw new ValorNaoPadronizado();
		}
		
		return respostaRespository.save(resposta);
	}
	
	
	
}
