package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.repository.CuisineRepository;
import com.marcusscalet.algafood.domain.service.CuisineRegistrationService;

@RestController
@RequestMapping(value = "/cuisines")
public class CuisineController {

	@Autowired
	private CuisineRepository cuisineRepository;

	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;

	@GetMapping
	public List<Cuisine> listAll() {
		return cuisineRepository.findAll();
	}

	@GetMapping("/{cuisineId}")
	public Cuisine find(@PathVariable Long cuisineId) {
		return cuisineRegistrationService.searchOrFail(cuisineId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cuisine add(@Valid @RequestBody Cuisine cuisine) {
		return cuisineRegistrationService.saveCuisine(cuisine);
	}

	@PutMapping("/{cuisineId}")
	public Cuisine update(@PathVariable Long cuisineId, @Valid @RequestBody Cuisine cuisine) {

		Cuisine currentCuisine = cuisineRegistrationService.searchOrFail(cuisineId);

		BeanUtils.copyProperties(cuisine, currentCuisine, "id");

		return cuisineRegistrationService.saveCuisine(currentCuisine);
	}

	@DeleteMapping("/{cuisineId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long cuisineId) {
		cuisineRegistrationService.removeCuisine(cuisineId);
	}

}