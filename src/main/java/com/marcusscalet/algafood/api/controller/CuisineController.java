package com.marcusscalet.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.marcusscalet.algafood.domain.exception.EntityInUseException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.repository.CuisineRepository;
import com.marcusscalet.algafood.domain.service.CuisineRegistrationService;

@RestController
@RequestMapping(value = "/cozinhas")
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
	public ResponseEntity<Cuisine> find(@PathVariable Long cuisineId) {
		Optional<Cuisine> cozinha = cuisineRepository.findById(cuisineId);

		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cuisine add(@RequestBody Cuisine cuisine) {
		return cuisineRegistrationService.saveCuisine(cuisine);
	}

	@PutMapping("/{cuisineId}")
	public ResponseEntity<Cuisine> update(@PathVariable Long cuisineId, @RequestBody Cuisine cuisine) {
		Optional<Cuisine> currentCuisine = cuisineRepository.findById(cuisineId);

		if (currentCuisine.isPresent()) {
			BeanUtils.copyProperties(cuisine, currentCuisine.get(), "id");

			Cuisine savedCuisine = cuisineRegistrationService.saveCuisine(currentCuisine.get());
			return ResponseEntity.ok(savedCuisine);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{cuisineId}")
	public ResponseEntity<?> remove(@PathVariable Long cuisineId) {
		try {
			cuisineRegistrationService.removeCuisine(cuisineId);
			return ResponseEntity.noContent().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}