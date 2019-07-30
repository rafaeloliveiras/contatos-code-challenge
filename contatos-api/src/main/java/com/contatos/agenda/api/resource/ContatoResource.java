package com.contatos.agenda.api.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.contatos.agenda.api.model.Contato;
import com.contatos.agenda.api.repository.ContatoRepository;

@RestController
@RequestMapping("/contatos")
public class ContatoResource {

	@Autowired
	private ContatoRepository contatoRepository;
	
	@GetMapping
	public List<Contato> exibirTodosOsContatos() {
		return contatoRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Contato> exibirContatoPorId(@Valid @PathVariable Long id) {
		Contato contatoSalvo = contatoRepository.findById(id).orElse(null);
		return contatoSalvo != null ? ResponseEntity.ok(contatoSalvo) : ResponseEntity.notFound().build();
	}
		
	@PostMapping
	public ResponseEntity<Contato> inserirContato(@RequestBody Contato contato) {
		Contato contatoSalvo = contatoRepository.save(contato);
		return ResponseEntity.status(HttpStatus.CREATED).body(contatoSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Contato> atualizarContato(@PathVariable Long id, @RequestBody Contato contato) {
		Contato contatoSalvo = contatoRepository.findById(id).orElse(null);
		BeanUtils.copyProperties(contato, contatoSalvo, "id");
		contatoRepository.save(contatoSalvo);
		
		return ResponseEntity.ok(contatoSalvo);
	}
	
	@DeleteMapping("/{id}")
	public void deletarUmContato(@PathVariable Long id) {
		Contato contato = contatoRepository.findById(id).orElse(null);
		
		if (contato == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		contatoRepository.deleteById(id);
	}
	
}
