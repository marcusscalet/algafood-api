package com.marcusscalet.algafood.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import com.marcusscalet.algafood.api.v2.assembler.CuisineInputDisassemblerV2;
import com.marcusscalet.algafood.api.v2.assembler.CuisineModelAssemblerV2;
import com.marcusscalet.algafood.api.v2.model.CuisineModelV2;
import com.marcusscalet.algafood.api.v2.model.input.CuisineInputV2;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.service.CuisineRegistrationService;

@RestController
@RequestMapping(path = "/v2/cuisines")
public class CuisineControllerV2{

	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;

	@Autowired
	private CuisineModelAssemblerV2 cuisineModelAssembler;
	
	@Autowired
	private CuisineInputDisassemblerV2 cuisineInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cuisine> pagedResourceAssembler;
	
	@GetMapping
	public PagedModel<CuisineModelV2> listAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cuisine> cuisinesPage = cuisineRegistrationService.findAll(pageable);
		
		PagedModel<CuisineModelV2> cuisinesPagedModel = pagedResourceAssembler
				.toModel(cuisinesPage, cuisineModelAssembler);
		
		return cuisinesPagedModel;
	}

	@GetMapping("/{cuisineId}")
	public CuisineModelV2 find(@PathVariable Long cuisineId) {
		Cuisine cuisine =  cuisineRegistrationService.searchOrFail(cuisineId);
		
		return cuisineModelAssembler.toModel(cuisine);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CuisineModelV2 add(@Valid @RequestBody CuisineInputV2 cuisineInput) {
		Cuisine cuisine = cuisineInputDisassembler.toDomainObject(cuisineInput);
		cuisine = cuisineRegistrationService.saveCuisine(cuisine);
		
		return cuisineModelAssembler.toModel(cuisine);
	}

	@PutMapping("/{cuisineId}")
	public CuisineModelV2 update(@PathVariable Long cuisineId, @Valid @RequestBody CuisineInputV2 cuisineInput) {

		Cuisine currentCuisine = cuisineRegistrationService.searchOrFail(cuisineId);

		cuisineInputDisassembler.copyToDomainObject(cuisineInput, currentCuisine);

		currentCuisine = cuisineRegistrationService.saveCuisine(currentCuisine);
		
		return cuisineModelAssembler.toModel(currentCuisine);
	}

	@DeleteMapping("/{cuisineId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long cuisineId) {
		cuisineRegistrationService.removeCuisine(cuisineId);
	}

}