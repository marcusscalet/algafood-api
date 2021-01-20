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

import com.marcusscalet.algafood.api.assembler.CuisineDTOAssembler;
import com.marcusscalet.algafood.api.assembler.CuisineInputDisassembler;
import com.marcusscalet.algafood.api.model.CuisineDTO;
import com.marcusscalet.algafood.api.model.input.CuisineInput;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.service.CuisineRegistrationService;

@RestController
@RequestMapping(value = "/cuisines")
public class CuisineController {

	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;

	@Autowired
	private CuisineDTOAssembler cuisineDTOAssembler;
	
	@Autowired
	private CuisineInputDisassembler cuisineInputDisassembler;
	
	@GetMapping
	public Page<CuisineDTO> listAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cuisine> cuisinesPage = cuisineRegistrationService.findAll(pageable);
		
		List<CuisineDTO> cuisinesDTO = cuisineDTOAssembler.toCollectionDTO(cuisinesPage.getContent());
		
		Page<CuisineDTO> cuisinesDTOPage = new PageImpl<>(cuisinesDTO, pageable, cuisinesPage.getTotalElements());
		
		return cuisinesDTOPage;
	}

	@GetMapping("/{cuisineId}")
	public CuisineDTO find(@PathVariable Long cuisineId) {
		Cuisine cuisine =  cuisineRegistrationService.searchOrFail(cuisineId);
		
		return cuisineDTOAssembler.toDTO(cuisine);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CuisineDTO add(@Valid @RequestBody CuisineInput cuisineInput) {
		Cuisine cuisine = cuisineInputDisassembler.toDomainObject(cuisineInput);
		cuisine = cuisineRegistrationService.saveCuisine(cuisine);
		
		return cuisineDTOAssembler.toDTO(cuisine);
	}

	@PutMapping("/{cuisineId}")
	public CuisineDTO update(@PathVariable Long cuisineId, @Valid @RequestBody CuisineInput cuisineInput) {

		Cuisine currentCuisine = cuisineRegistrationService.searchOrFail(cuisineId);

		cuisineInputDisassembler.copyToDomainObject(cuisineInput, currentCuisine);

		currentCuisine = cuisineRegistrationService.saveCuisine(currentCuisine);
		
		return cuisineDTOAssembler.toDTO(currentCuisine);
	}

	@DeleteMapping("/{cuisineId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long cuisineId) {
		cuisineRegistrationService.removeCuisine(cuisineId);
	}

}