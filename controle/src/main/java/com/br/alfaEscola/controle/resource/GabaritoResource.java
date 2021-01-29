package com.br.alfaEscola.controle.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.alfaEscola.controle.event.RecursoCriadoEvent;
import com.br.alfaEscola.controle.exceptionhandler.alfaEscolaExceptionHandler.Erro;
import com.br.alfaEscola.controle.model.Gabarito;
import com.br.alfaEscola.controle.repository.GabaritoRepository;
import com.br.alfaEscola.controle.service.GabaritoRespostaService;
import com.br.alfaEscola.controle.service.exception.IdInformadoInapropriada;
import com.br.alfaEscola.controle.service.exception.ListaRespostaVaziaInvalida;
import com.br.alfaEscola.controle.service.exception.PesoRespostaInvalida;
import com.br.alfaEscola.controle.service.exception.ValorNaoPadronizado;

@RestController
@RequestMapping("/gabaritos")
public class GabaritoResource {

	@Autowired
	private GabaritoRepository gabaritoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private GabaritoRespostaService GabaritoRespostaService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public List<Gabarito> listar() {
		return gabaritoRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Gabarito> criar(@Valid @RequestBody Gabarito gabarito, HttpServletResponse response) {
		Gabarito gabaritoSalvo = GabaritoRespostaService.salvar(gabarito);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, gabaritoSalvo.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(gabaritoSalvo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Gabarito> buscarPeloCodigo(@PathVariable Long id) {
		Optional<Gabarito> gabarito = this.gabaritoRepository.findById(id);
		return gabarito.isPresent() ? ResponseEntity.ok(gabarito.get()) : ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler({ListaRespostaVaziaInvalida.class})
	public ResponseEntity<Object> handleListaRespostaVaziaInvalida(ListaRespostaVaziaInvalida ex){
		String mensagemUsuario = messageSource.getMessage("gabarito.lista-resposta_vaiza", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}


	@ExceptionHandler({ValorNaoPadronizado.class})
	public ResponseEntity<Object> handleValorNaoPadronizado(ValorNaoPadronizado ex){
		String mensagemUsuario = messageSource.getMessage("resposta.peso-questao-invalido", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ExceptionHandler({PesoRespostaInvalida.class})
	public ResponseEntity<Object> handlePesoRespostaInvalida(PesoRespostaInvalida ex){
		String mensagemUsuario = messageSource.getMessage("gabarito.peso-invalido", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ExceptionHandler({IdInformadoInapropriada.class})
	public ResponseEntity<Object> handleIdInformadoInapropriada(IdInformadoInapropriada ex){
		String mensagemUsuario = messageSource.getMessage("gabarito.campo-inapropriado", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	

}
