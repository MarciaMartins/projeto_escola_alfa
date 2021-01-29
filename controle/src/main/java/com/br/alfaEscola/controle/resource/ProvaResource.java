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
import com.br.alfaEscola.controle.model.Prova;
import com.br.alfaEscola.controle.repository.ProvaRespository;

@RestController
@RequestMapping("/provas")
public class ProvaResource {
	
	@Autowired
	private ProvaRespository provaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Prova> listar(){
		return provaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Prova> criar(@Valid @RequestBody Prova prova, HttpServletResponse response) {
		Prova provaSalvo = provaRepository.save(prova);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, provaSalvo.getId()));			
		return 	ResponseEntity.status(HttpStatus.CREATED).body(provaSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Prova> buscarPeloCodigo(@PathVariable Long id) {
	    Optional<Prova> prova = this.provaRepository.findById(id);
	    return prova.isPresent() ? 
	            ResponseEntity.ok(prova.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		provaRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public Prova atualizar(@PathVariable Long id, @Valid @RequestBody Prova	prova){
		Prova provaSalvo = this.provaRepository.findById(id)
			      .orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(prova, provaSalvo, "id");
		return this.provaRepository.save(provaSalvo);
		
	}

}
