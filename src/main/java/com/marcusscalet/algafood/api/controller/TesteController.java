package com.marcusscalet.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.domain.model.Cozinha;
import com.marcusscalet.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepo;

//	@GetMapping("/cozinhas/por-nome")
//	public List<Cozinha> consultarPorNome(@RequestParam("nome") String nome) {
//		return cozinhaRepo.consultarPorNome(nome);
//
//	}
}
