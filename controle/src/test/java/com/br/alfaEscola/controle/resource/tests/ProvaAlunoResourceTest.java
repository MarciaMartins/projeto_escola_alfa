package com.br.alfaEscola.controle.resource.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import com.br.alfaEscola.controle.model.Aluno;
import com.br.alfaEscola.controle.model.Prova;
import com.br.alfaEscola.controle.model.ProvaAluno;
import com.br.alfaEscola.controle.model.RespostaAluno;
import com.br.alfaEscola.controle.model.ValorResposta;
import com.br.alfaEscola.controle.repository.ProvaAlunoRespository;
import com.br.alfaEscola.controle.resource.ProvaAlunoResource;
import com.br.alfaEscola.controle.service.ProvaAlunoService;

@ExtendWith(MockitoExtension.class)
public class ProvaAlunoResourceTest {

	@Mock
	private ApplicationEventPublisher publisher;

	@Mock
	private ProvaAlunoService provaAlunoService;
	
	@Mock
	private ProvaAlunoRespository provaAlunoRepository;
	
	@Mock
	private HttpServletResponse response;
	
	@InjectMocks
	private ProvaAlunoResource provaAlunoResource;
	
	@Test
	public void testListar() {
		List<ProvaAluno> provas = new ArrayList<>();
		provas.add(retornaMockProvaAluno());
		
		when(provaAlunoRepository.findAll()).thenReturn(provas);
		
		List<ProvaAluno> resposta = provaAlunoResource.listar();
		verify(provaAlunoRepository).findAll();
		
		assertThat(resposta.size()).isEqualTo(1);
		assertThat(resposta.get(0).getRespostasAluno().size()).isEqualTo(3);
	}

	private ProvaAluno retornaMockProvaAluno() {
		Aluno aluno = new Aluno(1L, "Aluno teste");
		Prova prova = new Prova(1L, "Prova");
		
		RespostaAluno respostaAluno1 = new RespostaAluno(ValorResposta.A);
		RespostaAluno respostaAluno2 = new RespostaAluno(ValorResposta.B);
		RespostaAluno respostaAluno3 = new RespostaAluno(ValorResposta.C);
		
		List<RespostaAluno> respostas = new ArrayList<>();
		respostas.add(respostaAluno1);
		respostas.add(respostaAluno2);
		respostas.add(respostaAluno3);
		
		return new ProvaAluno(aluno, prova, respostas);
	}
	
}
