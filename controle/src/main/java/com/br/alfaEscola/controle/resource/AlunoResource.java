package com.br.alfaEscola.controle.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.alfaEscola.controle.event.RecursoCriadoEvent;
import com.br.alfaEscola.controle.exceptionhandler.alfaEscolaExceptionHandler.Erro;
import com.br.alfaEscola.controle.model.Aluno;
import com.br.alfaEscola.controle.repository.AlunoRepository;
import com.br.alfaEscola.controle.service.AlunoService;
import com.br.alfaEscola.controle.service.exception.CapacidadeMaximaAtingida;

@RestController
@RequestMapping("/alunos")
public class AlunoResource {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private AlunoService alunoservice;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Aluno> listar(){
		return alunoRepository.findAll();
	}
	
	@GetMapping("/aprovados") 
	public List<Aluno> alunosAprovados(){
		List<Aluno> alunosAprovados = alunoservice.validaAprovacaoAluno();
		return alunosAprovados; 
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Aluno> buscarPeloCodigo(@PathVariable Long id) {
		Optional<Aluno> aluno = this.alunoRepository.findById(id);
		alunoservice.calcularNotaFinal(aluno.get());
		
		return aluno.isPresent() ? 
				ResponseEntity.ok(aluno.get()) : ResponseEntity.notFound().build();
	}
	
//	@GetMapping("/{id}/notaFinal")
//	public ResponseEntity<Object> consultarNotaFinal(@PathVariable Long id){
//		Optional<Aluno> alunoRecuperado = alunoRepository.findById(id);
//		Aluno aluno = alunoRecuperado.get();
//		alunoservice.calcularNotaFinal(aluno);
//		
//		return 	ResponseEntity.status(HttpStatus.OK).body(aluno);
//	}
	
	@PostMapping
	public ResponseEntity<Aluno> criar(@Valid @RequestBody Aluno aluno, HttpServletResponse response) {
		Aluno alunoSalvo = alunoservice.salvar(aluno);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, alunoSalvo.getId()));			
		return 	ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
	}
	
	@PutMapping("/{id}")
	public Aluno atualizar(@PathVariable Long id, @Valid @RequestBody Aluno aluno){
		Aluno alunoSalvo = this.alunoRepository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(aluno, alunoSalvo, "id");
		return this.alunoRepository.save(alunoSalvo);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		alunoRepository.deleteById(id);
	}
	
	@ExceptionHandler({CapacidadeMaximaAtingida.class})
	public ResponseEntity<Object> handleCapacidadeMaximaAtingida(CapacidadeMaximaAtingida ex){
		String mensagemUsuario = messageSource.getMessage("pessao.capacidade-maxima", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}
