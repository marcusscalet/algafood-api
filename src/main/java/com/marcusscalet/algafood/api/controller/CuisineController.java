package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.marcusscalet.algafood.api.assembler.CuisineModelAssembler;
import com.marcusscalet.algafood.api.assembler.CuisineInputDisassembler;
import com.marcusscalet.algafood.api.model.CuisineModel;
import com.marcusscalet.algafood.api.model.input.CuisineInput;
import com.marcusscalet.algafood.api.openapi.controller.CuisineControllerOpenApi;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.service.CuisineRegistrationService;

@RestController
@RequestMapping(value = "/cuisines")
public class CuisineController implements CuisineControllerOpenApi{

	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;

	@Autowired
	private CuisineModelAssembler cuisineModelAssembler;
	
	@Autowired
	private CuisineInputDisassembler cuisineInputDisassembler;
	
	@GetMapping
	public Page<CuisineModel> listAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cuisine> cuisinesPage = cuisineRegistrationService.findAll(pageable);
		
		List<CuisineModel> cuisinesModel = cuisineModelAssembler.toCollectionModel(cuisinesPage.getContent());
		
		Page<CuisineModel> cuisinesModelPage = new PageImpl<>(cuisinesModel, pageable, cuisinesPage.getTotalElements());
		
		return cuisinesModelPage;
	}

	@GetMapping("/{cuisineId}")
	public CuisineModel find(@PathVariable Long cuisineId) {
		Cuisine cuisine =  cuisineRegistrationService.searchOrFail(cuisineId);
		
		return cuisineModelAssembler.toModel(cuisine);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CuisineModel add(@Valid @RequestBody CuisineInput cuisineInput) {
		Cuisine cuisine = cuisineInputDisassembler.toDomainObject(cuisineInput);
		cuisine = cuisineRegistrationService.saveCuisine(cuisine);
		
		return cuisineModelAssembler.toModel(cuisine);
	}

	@PutMapping("/{cuisineId}")
	public CuisineModel update(@PathVariable Long cuisineId, @Valid @RequestBody CuisineInput cuisineInput) {

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