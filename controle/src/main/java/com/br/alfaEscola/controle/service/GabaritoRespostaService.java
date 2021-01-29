package com.br.alfaEscola.controle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.alfaEscola.controle.model.Aluno;
import com.br.alfaEscola.controle.model.Gabarito;
import com.br.alfaEscola.controle.model.Prova;
import com.br.alfaEscola.controle.model.Resposta;
import com.br.alfaEscola.controle.repository.GabaritoRepository;
import com.br.alfaEscola.controle.repository.ProvaRespository;
import com.br.alfaEscola.controle.repository.RespostaRepository;
import com.br.alfaEscola.controle.service.exception.IdInformadoInapropriada;
import com.br.alfaEscola.controle.service.exception.ListaRespostaVaziaInvalida;
import com.br.alfaEscola.controle.service.exception.PesoRespostaInvalida;
import com.br.alfaEscola.controle.service.exception.ValorNaoPadronizado;

@Service
public class GabaritoRespostaService {
	
	@Autowired
	private GabaritoRepository gabaritoRepository;
	
	@Autowired
	private RespostaRepository respostaRepository;
	
	@Autowired 
	private ProvaRespository provaRespository;
	
	public Gabarito salvar(Gabarito gabarito) {
		validaGabarito(gabarito);
			Prova prova = new Prova(gabarito);
									
			List<Resposta> respostas = gabarito.getRespostas();
			gabarito.setQuantidadePerguntas(respostas.size());
			Gabarito gabaritoSalvo = gabaritoRepository.save(gabarito);
						
			for (Resposta resposta : respostas) {
				resposta.setGabarito(gabaritoSalvo);
				if(resposta.getPesoQuestao()<0) {
					 throw new ValorNaoPadronizado();
				}
			}	
			
			respostaRepository.saveAll(respostas);
							
			prova.setGabaritoId(gabaritoSalvo.getId());
			Prova provaSalvo = provaRespository.save(prova);
					
			gabarito.setProva(provaSalvo);
			gabaritoRepository.save(gabarito);
			
			
			return gabaritoSalvo;
			
	}
	
	private void validaGabarito(Gabarito gabarito) {
		if(gabarito.getRespostas().isEmpty()) {
			throw new ListaRespostaVaziaInvalida();
		}
				
		List<Resposta> listaResposta = gabarito.getRespostas();
		for (Resposta resposta : listaResposta) {
			if(resposta.getId() !=null) {
				throw new IdInformadoInapropriada();
			}
			if(resposta.getPesoQuestao()<=0){
				throw new PesoRespostaInvalida();
			}
		}
	
	}
	
	
	
	

}
