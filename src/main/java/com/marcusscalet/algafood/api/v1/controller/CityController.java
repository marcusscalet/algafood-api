package com.marcusscalet.algafood.api.v1.controller;

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
import com.marcusscalet.algafood.api.v1.assembler.CityInputDisassembler;
import com.marcusscalet.algafood.api.v1.assembler.CityModelAssembler;
import com.marcusscalet.algafood.api.v1.model.CityModel;
import com.marcusscalet.algafood.api.v1.model.input.CityInput;
import com.marcusscalet.algafood.api.v1.openapi.controller.CityControllerOpenApi;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.StateNotFoundException;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.service.CityRegistrationService;

@RestController
@RequestMapping(path = "/v1/cities")
public class CityController implements CityControllerOpenApi{

	@Autowired
	private CityRegistrationService cityRegistrationService;

	@Autowired
	private CityModelAssembler cityModelAssembler;
	
	@Autowired
	private CityInputDisassembler cityInputDisassembler;
	
	@Deprecated
	@GetMapping
	public CollectionModel<CityModel> listAll() {
		List<City> citiesList = cityRegistrationService.listAll();
		
		return cityModelAssembler.toCollectionModel(citiesList);
	}

	@GetMapping("/{cityId}")
	public CityModel find(@PathVariable Long cityId) {
		
		return cityModelAssembler.toModel(cityRegistrationService.searchOrFail(cityId));

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CityModel add(@Valid @RequestBody CityInput cityInput) {
		try {
			City city = cityInputDisassembler.toDomainObject(cityInput);

			city = cityRegistrationService.saveCity(city);
			
			CityModel cityModel = cityModelAssembler.toModel(city);
			
			ResourceUriHelper.addUriInReponseHeader(cityModel.getId());
			
			return cityModel;
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{cityId}")
	public CityModel update(
			@PathVariable Long cityId,
			@Valid @RequestBody CityInput cityInput) {
		
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