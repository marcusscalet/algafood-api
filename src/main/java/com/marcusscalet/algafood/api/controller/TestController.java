package com.marcusscalet.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.domain.repository.CuisineRepository;

@RestController
@RequestMapping("/teste")
public class TestController {

	@Autowired
	private CuisineRepository cuisineRepository;

//	@GetMapping("/cozinhas/por-nome")
//	public List<Cuisine> consultByName(@RequestParam("name") String name) {
//		return cuisineRepository.findByName(name);
//	}
}
