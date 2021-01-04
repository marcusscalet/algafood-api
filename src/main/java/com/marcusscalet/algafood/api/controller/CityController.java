package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.marcusscalet.algafood.api.assembler.CityDTOAssembler;
import com.marcusscalet.algafood.api.assembler.CityInputDisassembler;
import com.marcusscalet.algafood.api.model.CityDTO;
import com.marcusscalet.algafood.api.model.input.CityInput;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.StateNotFoundException;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.service.CityRegistrationService;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

	@Autowired
	private CityRegistrationService cityRegistrationService;

	@Autowired
	private CityDTOAssembler cityDTOAssembler;
	
	@Autowired
	private CityInputDisassembler cityInputDisassembler;
	
	@GetMapping
	public List<CityDTO> listAll() {
		List<City> citiesList = cityRegistrationService.listAll();
		
		return cityDTOAssembler.toCollectionDTO(citiesList);
	}

	@GetMapping("/{cityId}")
	public CityDTO find(@PathVariable Long cityId) {
		City city = cityRegistrationService.searchOrFail(cityId);
		
		return cityDTOAssembler.toDTO(city);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CityDTO add(@Valid @RequestBody CityInput cityInput) {
		try {
			City city = cityInputDisassembler.toDomainObject(cityInput);

			city = cityRegistrationService.saveCity(city);
			
			return cityDTOAssembler.toDTO(city);
			
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{cityId}")
	public CityDTO update(@PathVariable Long cityId, @Valid @RequestBody CityInput cityInput) {
		try {
			City currentCity = cityRegistrationService.searchOrFail(cityId);

			cityInputDisassembler.copyToDomainObject(cityInput, currentCity);
			
			return cityDTOAssembler.toDTO(cityRegistrationService.saveCity(currentCity));
			
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@DeleteMapping("/{cityId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long cityId) {
		cityRegistrationService.remove(cityId);
	}
}