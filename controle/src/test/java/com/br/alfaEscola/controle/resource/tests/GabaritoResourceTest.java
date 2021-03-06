package com.br.alfaEscola.controle.resource.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.br.alfaEscola.controle.model.Gabarito;
import com.br.alfaEscola.controle.model.Resposta;
import com.br.alfaEscola.controle.model.ValorResposta;
import com.br.alfaEscola.controle.repository.GabaritoRepository;
import com.br.alfaEscola.controle.resource.GabaritoResource;
import com.br.alfaEscola.controle.service.GabaritoRespostaService;

@ExtendWith(MockitoExtension.class)
public class GabaritoResourceTest {

	@Mock
	private ApplicationEventPublisher publisher;

	@Mock
	private GabaritoRespostaService gabaritoRespostaService;
	
	@Mock
	private GabaritoRepository gabaritoRepository;
	
	@Mock
	private HttpServletResponse response;
	
	@InjectMocks
	private GabaritoResource gabaritoResource;
	
	@Test
	public void testListar() {
		List<Gabarito> listaGabaritos = new ArrayList<>();
		listaGabaritos.add(retornaMockGabarito());
		
		when(gabaritoRepository.findAll()).thenReturn(listaGabaritos);
		
		List<Gabarito> gabaritos = gabaritoResource.listar();
		verify(gabaritoRepository).findAll();
		
		assertThat(gabaritos.size()).isEqualTo(1);
		assertThat(gabaritos.get(0).getRespostas().size()).isEqualTo(3);
	}
	
	@Test
	public void testCriar() {
		Gabarito gabarito = retornaMockGabarito();
		when(gabaritoRespostaService.salvar(any())).thenReturn(gabarito);
		
		ResponseEntity<Gabarito> resposta = gabaritoResource.criar(gabarito, response);
		verify(gabaritoRespostaService).salvar(any());
		verify(publisher).publishEvent(any());
		
		assertThat(resposta.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		assertThat(resposta.getBody().getRespostas().size()).isEqualTo(3);
	}
	
	@Test
	public void testBuscarCodigo() {
		when(gabaritoRepository.findById(any())).thenReturn(Optional.of(retornaMockGabarito()));
		
		ResponseEntity<Gabarito> resposta = gabaritoResource.buscarPeloCodigo(1L);
		verify(gabaritoRepository).findById(any());
		
		assertThat(resposta.getBody().getRespostas().size()).isEqualTo(3);
	}
	
	@Test
	public void testBuscarCodigoNaoRetornaGabarito() {
		when(gabaritoRepository.findById(any())).thenReturn(Optional.empty());
		
		ResponseEntity<Gabarito> resposta = gabaritoResource.buscarPeloCodigo(1L);
		verify(gabaritoRepository).findById(any());
		
		assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	private Gabarito retornaMockGabarito() {
		Gabarito gabarito = new Gabarito();
		
		Resposta resposta1 = new Resposta(ValorResposta.A);
		Resposta resposta2 = new Resposta(ValorResposta.C);
		Resposta resposta3 = new Resposta(ValorResposta.D);
		
		List<Resposta> listaRespostas = new ArrayList<>();
		listaRespostas.add(resposta1);
		listaRespostas.add(resposta2);
		listaRespostas.add(resposta3);
		
		gabarito.setDescricao("Novo gabarito");
		gabarito.setRespostas(listaRespostas);
		return gabarito;
	}
}
