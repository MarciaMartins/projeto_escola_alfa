package com.br.alfaEscola.controle.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.alfaEscola.controle.model.Aluno;
import com.br.alfaEscola.controle.model.ProvaAluno;
import com.br.alfaEscola.controle.repository.AlunoRepository;
import com.br.alfaEscola.controle.repository.ProvaAlunoRespository;
import com.br.alfaEscola.controle.service.exception.CapacidadeMaximaAtingida;



@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private ProvaAlunoRespository provaAlunoRespository;

	public Aluno salvar(Aluno aluno) {
		
		if(alunoRepository.count()==aluno.getMaximo()) {
			throw new CapacidadeMaximaAtingida();
		}
		
		return alunoRepository.save(aluno);
	}
	
	public void calcularNotaFinal(Aluno aluno) {
		List<ProvaAluno> provasAlunos = provaAlunoRespository.findAll();
		int somaTodasProvas=0;
		int qtdProvas=0;
		for (ProvaAluno provaAluno : provasAlunos) {
			if (provaAluno.getAluno().getId().equals(aluno.getId())) {
				somaTodasProvas=somaTodasProvas+provaAluno.getNotaProva();
				System.out.println("Soma:"+somaTodasProvas);
				qtdProvas++;
			}
		}
		if(qtdProvas>0) {
			System.out.println("qtdProvas"+qtdProvas);
			int mediaAluno = somaTodasProvas/qtdProvas;
			aluno.setNotaFinal(mediaAluno);
		}else{
				aluno.setNotaFinal(0);
		}
		alunoRepository.save(aluno);
	}
	
	
	  public List<Aluno> validaAprovacaoAluno() { 
		  List<Aluno> alunos = alunoRepository.findAll(); 
		  List<Aluno> alunosAprovados = new ArrayList<Aluno>();;
		  for (Aluno aluno : alunos) {
			  calcularNotaFinal(aluno);
			  System.out.println(">>"+aluno.getNotaFinal());
		  }
	  
		  for (Aluno aluno : alunos) {
			  if(aluno.getNotaFinal()>7) {
				  alunosAprovados.add(aluno);
			  }
		  }
		  
		 
		  	  
		  return alunosAprovados;
	}
	  
	 
	 
	 
}
