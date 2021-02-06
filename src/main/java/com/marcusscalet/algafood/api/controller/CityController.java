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
import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.CityDTO;
import com.marcusscalet.algafood.api.model.input.CityInput;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.StateNotFoundException;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.service.CityRegistrationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "City")
@RestController
@RequestMapping(value = "/cities")
public class CityController {

	@Autowired
	private CityRegistrationService cityRegistrationService;

	@Autowired
	private CityDTOAssembler cityDTOAssembler;
	
	@Autowired
	private CityInputDisassembler cityInputDisassembler;
	
	@ApiOperation("List all cities")
	@GetMapping
	public List<CityDTO> listAll() {
		List<City> citiesList = cityRegistrationService.listAll();
		
		return cityDTOAssembler.toCollectionDTO(citiesList);
	}

	@ApiOperation("Find city by ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "City ID invalid", response = Problem.class),
		@ApiResponse(code = 404, message = "City not found", response = Problem.class)
	})
	@GetMapping("/{cityId}")
	public CityDTO find(@ApiParam("ID of a city") @PathVariable Long cityId) {
		City city = cityRegistrationService.searchOrFail(cityId);
		
		return cityDTOAssembler.toDTO(city);
	}

	@ApiOperation("Register a new city")
	@ApiResponses({
		@ApiResponse(code = 201, message = "City created"),
		@ApiResponse(code = 404, message = "City not found", response = Problem.class)
	})
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

	@ApiOperation("Update city By Id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "City updated"),
		@ApiResponse(code = 404, message = "City not found", response = Problem.class)
	})
	@PutMapping("/{cityId}")
	public CityDTO update(
			@ApiParam("ID of a city")
			@PathVariable Long cityId,
			@ApiParam(name = "body", value = "Representação de uma cidade com os novos dados")
			@Valid @RequestBody CityInput cityInput) {
		
		try {
			City currentCity = cityRegistrationService.searchOrFail(cityId);

			cityInputDisassembler.copyToDomainObject(cityInput, currentCity);
			
			return cityDTOAssembler.toDTO(cityRegistrationService.saveCity(currentCity));
			
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@ApiOperation("Delete city by ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "City removed"),
		@ApiResponse(code = 404, message = "City not found", response = Problem.class)
	})
	@DeleteMapping("/{cityId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@ApiParam("ID of a city")@PathVariable Long cityId) {
		cityRegistrationService.remove(cityId);
	}
}