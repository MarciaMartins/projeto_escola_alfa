package com.br.alfaEscola.controle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.alfaEscola.controle.model.Gabarito;

public interface GabaritoRepository extends JpaRepository<Gabarito, Long> {

	@Query(value = "SELECT id FROM Gabarito WHERE prova_id = ?", nativeQuery = true)
	  Gabarito findByProvaId(Long id);
	
	
	
}
