package com.br.alfaEscola.controle.resource.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.br.alfaEscola.controle.model.Aluno;
import com.br.alfaEscola.controle.repository.AlunoRepository;
import com.br.alfaEscola.controle.repository.ProvaAlunoRespository;
import com.br.alfaEscola.controle.resource.AlunoResource;
import com.br.alfaEscola.controle.service.AlunoService;

@ExtendWith(MockitoExtension.class)
public class AlunoResourceTest {

	@Mock
	private AlunoRepository alunoRepository;
	
	@Mock
	private ProvaAlunoRespository provaAlunoRespository;
	
	@Mock
	private AlunoService alunoService;
	
	@Mock
	private HttpServletResponse httpServletResponse;
	
	@Mock
	private ApplicationEventPublisher publisher;
	
	@InjectMocks
	private AlunoResource alunoResource;
	
	@Test
	public void testListar() {
		when(alunoRepository.findAll()).thenReturn(retornaMockListaAlunos());
		
		List<Aluno> resposta = alunoResource.listar();
		verify(alunoRepository).findAll();
		
		assertThat(resposta.size()).isEqualTo(2);
		assertThat(resposta.get(0).getNome()).containsIgnoringCase("aluno");
	}
	
	@Test
	public void testAlunosAprovados() {
		when(alunoService.validaAprovacaoAluno()).thenReturn(retornaMockListaAlunos());
		
		List<Aluno> alunosAprovados = alunoResource.alunosAprovados();
		verify(alunoService).validaAprovacaoAluno();
		
		assertThat(alunosAprovados.size()).isEqualTo(2);
	}
	
	@Test
	public void testBuscarPeloCodigo() {
		when(alunoRepository.findById(anyLong())).thenReturn(Optional.of(new Aluno(1L, "Aluno")));
		
		ResponseEntity<Aluno> resposta = alunoResource.buscarPeloCodigo(1L);
		verify(alunoRepository).findById(anyLong());
		
		assertThat(resposta.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(resposta.getBody().getNome()).isEqualTo("Aluno");
	}

	@Test
	public void testCriar(){
		Aluno novoAluno = new Aluno(1L, "Aluno");
		when(alunoService.salvar(any())).thenReturn(novoAluno);
		
		ResponseEntity<Aluno> resposta = alunoResource.criar(novoAluno, httpServletResponse);
		verify(alunoService).salvar(any());
		verify(publisher).publishEvent(any());
		
		assertThat(resposta.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		assertThat(resposta.getBody().getNome()).isEqualToIgnoringCase("aluno");
	}
	
	@Test
	public void testAtualizar() {
		Aluno novoAluno = new Aluno(1L, "Aluno");
		when(alunoRepository.findById(anyLong())).thenReturn(Optional.of(novoAluno));
		when(alunoRepository.save(any())).thenReturn(novoAluno);
		
		Aluno alunoSalvo = alunoResource.atualizar(1L, new Aluno(1L, "Aluno atualizado"));
		
		assertThat(alunoSalvo.getNome()).contains("atualizado");
	}
	
	private List<Aluno> retornaMockListaAlunos() {
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		Aluno aluno = new Aluno();
		aluno.setId(1L);
		aluno.setNome("Aluno de teste");
		
		Aluno aluno2 = new Aluno();
		aluno.setId(2L);
		aluno.setNome("Outro aluno de teste");
		alunos.add(aluno);
		alunos.add(aluno2);
		
		return alunos;
	}
}
