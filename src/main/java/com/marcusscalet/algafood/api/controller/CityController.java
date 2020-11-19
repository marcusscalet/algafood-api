package com.marcusscalet.algafood.api.controller;

import java.util.List;

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

import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.exception.GenericException;
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
	public City find(@PathVariable Long cityId) {
		return cityRegistrationService.searchOrFail(cityId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public City add(@RequestBody City city) {
		try {
			return cityRegistrationService.saveCity(city);
		} catch (EntityNotFoundException e) {
			throw new GenericException(e.getMessage());
		}
	}

	@PutMapping("/{cityId}")
	public City update(@PathVariable Long cityId, @RequestBody City city) {
		City currentCity = cityRegistrationService.searchOrFail(cityId);

		BeanUtils.copyProperties(city, currentCity, "id");

		try {
			return cityRegistrationService.saveCity(currentCity);
		} catch (EntityNotFoundException e) {
			throw new GenericException(e.getMessage());
		}
	}

	@DeleteMapping("/{cityId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long cityId) {
		cityRegistrationService.remove(cityId);
	}

}