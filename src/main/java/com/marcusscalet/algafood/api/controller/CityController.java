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
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.domain.exception.EntityInUseException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.repository.CityRepository;
import com.marcusscalet.algafood.domain.service.CityRegistrationService;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CityRegistrationService cityRegistrationService;

	@GetMapping
	public List<City> listAll() {
		return cityRepository.findAll();
	}

	@GetMapping("/{cityId}")
	public ResponseEntity<City> find(@PathVariable Long cityId) {
		Optional<City> foundCity = cityRepository.findById(cityId);

		if (foundCity != null) {
			return ResponseEntity.ok(foundCity.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody City city) {
		try {
			city = cityRegistrationService.saveCity(city);

			return ResponseEntity.status(HttpStatus.CREATED).body(city);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{cityId}")
	public ResponseEntity<?> update(@PathVariable Long cityId, @RequestBody City city) {
		try {
			// Podemos usar o orElse(null) também, que retorna a instância de cidade
			// dentro do Optional, ou null, caso ele esteja vazio,
			// mas nesse caso, temos a responsabilidade de tomar cuidado com
			// NullPointerException
			City mentionedCity = cityRepository.findById(cityId).orElse(null);

			if (mentionedCity != null) {
				BeanUtils.copyProperties(city, mentionedCity, "id");

				mentionedCity = cityRegistrationService.saveCity(mentionedCity);
				return ResponseEntity.ok(mentionedCity);
			}

			return ResponseEntity.notFound().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{cityId}")
	public ResponseEntity<City> remove(@PathVariable Long cityId) {
		try {
			cityRegistrationService.remove(cityId);
			return ResponseEntity.noContent().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}