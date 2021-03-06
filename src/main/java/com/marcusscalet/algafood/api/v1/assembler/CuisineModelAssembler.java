package com.marcusscalet.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.api.v1.controller.CuisineController;
import com.marcusscalet.algafood.api.v1.model.CuisineModel;
import com.marcusscalet.algafood.domain.model.Cuisine;

@Component
public class CuisineModelAssembler extends RepresentationModelAssemblerSupport<Cuisine, CuisineModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public CuisineModelAssembler() {
		super(CuisineController.class, CuisineModel.class);
	}

	@Override
	public CuisineModel toModel(Cuisine cuisine) {
		CuisineModel cuisineModel = createModelWithId(cuisine.getId(), cuisine);
		modelMapper.map(cuisine, cuisineModel);

		cuisineModel.add(algaLinks.linkToCuisines("cuisines"));

		return cuisineModel;
	}

}
