package com.br.alfaEscola.controle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.alfaEscola.controle.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
