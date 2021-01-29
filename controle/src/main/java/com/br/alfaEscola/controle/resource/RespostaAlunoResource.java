package com.br.alfaEscola.controle.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.alfaEscola.controle.event.RecursoCriadoEvent;
import com.br.alfaEscola.controle.model.RespostaAluno;
import com.br.alfaEscola.controle.repository.RespostaAlunoRepository;

@RestController
@RequestMapping("/respostasAlunos")
public class RespostaAlunoResource {
	
	@Autowired
	private RespostaAlunoRepository respostaAlunoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<RespostaAluno> listar(){
		return respostaAlunoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<RespostaAluno> criar(@Valid @RequestBody RespostaAluno respostaAluno, HttpServletResponse response) {
		RespostaAluno respostaAlunoSalvo = respostaAlunoRepository.save(respostaAluno);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, respostaAlunoSalvo.getId()));			
		return 	ResponseEntity.status(HttpStatus.CREATED).body(respostaAlunoSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RespostaAluno> buscarPeloCodigo(@PathVariable Long id) {
	    Optional<RespostaAluno> respostaAluno = this.respostaAlunoRepository.findById(id);
	    return respostaAluno.isPresent() ? 
	            ResponseEntity.ok(respostaAluno.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		respostaAlunoRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public RespostaAluno atualizar(@PathVariable Long id, @Valid @RequestBody RespostaAluno respostaAluno){
		RespostaAluno respostaAlunoSalvo = this.respostaAlunoRepository.findById(id)
			      .orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(respostaAluno, respostaAlunoSalvo, "id");
		return this.respostaAlunoRepository.save(respostaAlunoSalvo);
		
	}
}
