package com.contatos.agenda.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contatos.agenda.api.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
	
}
