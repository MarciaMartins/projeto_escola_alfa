package com.br.alfaEscola.controle.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.alfaEscola.controle.model.Aluno;
import com.br.alfaEscola.controle.model.Gabarito;
import com.br.alfaEscola.controle.model.Prova;
import com.br.alfaEscola.controle.model.ProvaAluno;
import com.br.alfaEscola.controle.model.Resposta;
import com.br.alfaEscola.controle.model.RespostaAluno;
import com.br.alfaEscola.controle.repository.AlunoRepository;
import com.br.alfaEscola.controle.repository.GabaritoRepository;
import com.br.alfaEscola.controle.repository.ProvaAlunoRespository;
import com.br.alfaEscola.controle.repository.ProvaRespository;
import com.br.alfaEscola.controle.repository.RespostaAlunoRepository;
import com.br.alfaEscola.controle.service.exception.AlunoInexistente;
import com.br.alfaEscola.controle.service.exception.CadastroProvaAlunoExistente;
import com.br.alfaEscola.controle.service.exception.ListaRespostaVaziaInvalida;
import com.br.alfaEscola.controle.service.exception.ParametroNuloInvalido;
import com.br.alfaEscola.controle.service.exception.ProvaInexistente;
import com.br.alfaEscola.controle.service.exception.QuantidadeDeRespostasInadequada;
import com.br.alfaEscola.controle.service.exception.RespostaNulaInvalida;

@Service
public class ProvaAlunoService {

	@Autowired
	private ProvaAlunoRespository provaAlunoRespository;

	@Autowired
	private RespostaAlunoRepository respostaAlunoRepository;
	
	@Autowired
	private GabaritoRepository gabaritoRepository;
	
	@Autowired
	private ProvaRespository provaRespository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	
		

	public ProvaAluno salvar(ProvaAluno provaAluno) {
		validaProvaAluno(provaAluno);
		Gabarito gabarito = ValidaTamanhoResposta(provaAluno);
		
		ProvaAluno provaAlunoSalva = provaAlunoRespository.save(provaAluno);
		
		List<RespostaAluno> respostasAluno = provaAluno.getRespostasAluno();
		for (RespostaAluno respostaAluno : respostasAluno) {
			respostaAluno.setProvaAluno(provaAlunoSalva);
		}
						
		respostaAlunoRepository.saveAll(respostasAluno);
		
		int notaProva = notaFinalAluno(provaAlunoSalva, gabarito);
		System.out.println("Nota Prova: " +notaProva);
		
		Aluno alunoRecuperado = recuperaAlunoCodigo(provaAluno.getAluno().getId());
		System.out.println("Id do aluno:"+alunoRecuperado.getId());
		
		alunoRecuperado.getNome();
		
		alunoRecuperado.getNotaFinal();
		alunoRepository.save(alunoRecuperado);
		provaAluno.getProva().getDescricao();
		provaAluno.setAluno(alunoRecuperado);
		provaAluno.setNotaProva(notaProva);
		provaAlunoRespository.save(provaAluno);
		
		
		return provaAluno;
	}
	
	public void validaProvaAluno(ProvaAluno provaAluno) {
			
		if(provaAluno.getAluno().getId()==null || provaAluno.getProva().getId()==null) {
			throw new ParametroNuloInvalido();
		}
		validaAluno(provaAluno.getAluno().getId());
		validoProva(provaAluno.getProva().getId());
						
		if(provaAluno.getRespostasAluno().isEmpty()) {
			throw new ListaRespostaVaziaInvalida();
		}
		
		List<RespostaAluno> respostasAluno = provaAluno.getRespostasAluno();
		for (RespostaAluno respostaAluno : respostasAluno) {
			if(respostaAluno.getResposta()==null) {
				throw new RespostaNulaInvalida();
			}
		}
		
		validaCadastroExistente(provaAluno);
	}
	
	
	
	private boolean validoProva(Long id) {
		boolean controle=false;
		List<Prova> provasLista = provaRespository.findAll();
		for (Prova prova : provasLista) {
			if(prova.getId().equals(id)) {
				controle=true;
			}
		}
		if(!controle) {
			throw new ProvaInexistente();
		}
		return controle;
	}
	
	
	private boolean validaAluno(Long id) {
		boolean controle=false;
		List<Aluno> alunoLista= alunoRepository.findAll();
		for (Aluno aluno : alunoLista) {
			if(aluno.getId().equals(id)) {
				controle=true;
				
			}
		}
		if(!controle) {
			throw new AlunoInexistente();
		}
		return controle;
	}
	
	private void validaCadastroExistente(ProvaAluno provaAluno) {
		List<ProvaAluno> listaprovaAluno = provaAlunoRespository.findAll();
		
		Long idAluno = provaAluno.getAluno().getId();
		Long idProva = provaAluno.getProva().getId();
		
		for (ProvaAluno provaAlunoR : listaprovaAluno) {
			if (provaAlunoR.getProva().getId().equals(idProva) &&
					provaAlunoR.getAluno().getId().equals(idAluno)) {
				throw new CadastroProvaAlunoExistente();
			}
		}
	}
	
	private int notaFinalAluno(ProvaAluno provaAlunoSalva, Gabarito gabarito) {
		
		List<RespostaAluno> respostasAluno = provaAlunoSalva.getRespostasAluno();
		List<Resposta> respostasGabarito = gabarito.getRespostas();
		
		float somatorio=0;
		int peso=0;
		float mediaPonderada=0;
		
		System.out.println("Size>>"+respostasGabarito.size());
		
		int incremental=0;
		for (Resposta respostaGabarito : respostasGabarito) {
			if(respostaGabarito.getResposta().equals(respostasAluno.get(incremental).getResposta())) {
				somatorio = somatorio+(respostaGabarito.getPesoQuestao()*1);
				System.out.println("Peso"+peso);
				System.out.println("Somatorio"+somatorio);
			}
			peso=respostaGabarito.getPesoQuestao()+peso;
			incremental++;
		}
		
		if(peso>0) {
			mediaPonderada=somatorio/peso;
			float b = mediaPonderada*10;
			int a = Math.round(b); 
			System.out.println(">>"+a);	
			return a;
		}
		else return 0;
	}
	
	public Gabarito ValidaTamanhoResposta(ProvaAluno provaAluno) {
		List<RespostaAluno> respostasAluno = provaAluno.getRespostasAluno();
		Long idProva = provaAluno.getProva().getId();
		System.out.println("<><>"+idProva);
		Optional<Prova> prova = provaRespository.findById(idProva);
		Prova provaRecuperado = prova.get();
		Long idGabarito = provaRecuperado.getGabaritoId();
		System.out.println("><><"+idGabarito);
		Optional<Gabarito> gabaritoBanco = gabaritoRepository.findById(idGabarito);
		Gabarito gabaritoConsulta = gabaritoBanco.get();
		int tamanho = gabaritoConsulta.getQuantidadeRespostas();
		System.out.println("<><>"+tamanho);
		if(respostasAluno.size()> gabaritoConsulta.getQuantidadeRespostas() ||
				 respostasAluno.size() < gabaritoConsulta.getQuantidadeRespostas()) {
			throw new QuantidadeDeRespostasInadequada();
		}
		return gabaritoConsulta;
	}
	
	
	public Prova recuperaProvaCodigo(Long id) {
		Optional<Prova> recurepara = provaRespository.findById(id);
		Prova prova1 = recurepara.get();
		return prova1;
	}
	
	public Gabarito recuperaGabaritoCodigo(Long id) {
		Optional<Gabarito> gabaritoRecuperado = gabaritoRepository.findById(id);
		Gabarito gabarito = gabaritoRecuperado.get();
		return gabarito;
	}
	
	public Aluno recuperaAlunoCodigo(Long id) {
		Optional<Aluno> alunoRecuperado = alunoRepository.findById(id);
		Aluno aluno = alunoRecuperado.get();
		return aluno;
	}

	
	
	
}
