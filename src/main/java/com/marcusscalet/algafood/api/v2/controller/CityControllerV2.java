package com.marcusscalet.algafood.api.v2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.marcusscalet.algafood.api.ResourceUriHelper;
import com.marcusscalet.algafood.api.v2.assembler.CityInputDisassemblerV2;
import com.marcusscalet.algafood.api.v2.assembler.CityModelAssemblerV2;
import com.marcusscalet.algafood.api.v2.model.CityModelV2;
import com.marcusscalet.algafood.api.v2.model.input.CityInputV2;
import com.marcusscalet.algafood.core.web.AlgaMediaTypes;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.StateNotFoundException;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.service.CityRegistrationService;

@RestController
@RequestMapping(path = "/cities", produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
public class CityControllerV2 {

	@Autowired
	private CityRegistrationService cityRegistrationService;

	@Autowired
	private CityModelAssemblerV2 cityModelAssembler;
	
	@Autowired
	private CityInputDisassemblerV2 cityInputDisassembler;
	
	@GetMapping
	public CollectionModel<CityModelV2> listAll() {
		List<City> citiesList = cityRegistrationService.listAll();
		
		return cityModelAssembler.toCollectionModel(citiesList);
	}

	@GetMapping("/{cityId}")
	public CityModelV2 find(@PathVariable Long cityId) {
		
		return cityModelAssembler.toModel(cityRegistrationService.searchOrFail(cityId));

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CityModelV2 add(@Valid @RequestBody CityInputV2 cityInput) {
		try {
			City city = cityInputDisassembler.toDomainObject(cityInput);

			city = cityRegistrationService.saveCity(city);
			
			CityModelV2 cityModel = cityModelAssembler.toModel(city);
			
			ResourceUriHelper.addUriInReponseHeader(cityModel.getCityId());
			
			return cityModel;
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{cityId}")
	public CityModelV2 update(
			@PathVariable Long cityId,
			@Valid @RequestBody CityInputV2 cityInput) {
		
		try {
			City currentCity = cityRegistrationService.searchOrFail(cityId);

			cityInputDisassembler.copyToDomainObject(cityInput, currentCity);
			
			return cityModelAssembler.toModel(cityRegistrationService.saveCity(currentCity));
			
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