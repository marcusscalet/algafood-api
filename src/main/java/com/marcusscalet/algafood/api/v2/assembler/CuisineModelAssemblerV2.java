package com.marcusscalet.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v2.AlgaLinksV2;
import com.marcusscalet.algafood.api.v2.controller.CuisineControllerV2;
import com.marcusscalet.algafood.api.v2.model.CuisineModelV2;
import com.marcusscalet.algafood.domain.model.Cuisine;

@Component
public class CuisineModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cuisine, CuisineModelV2> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksV2 algaLinksV2;

	public CuisineModelAssemblerV2() {
		super(CuisineControllerV2.class, CuisineModelV2.class);
	}

	@Override
	public CuisineModelV2 toModel(Cuisine cuisine) {
		CuisineModelV2 cuisineModel = createModelWithId(cuisine.getId(), cuisine);
		modelMapper.map(cuisine, cuisineModel);

		cuisineModel.add(algaLinksV2.linkToCuisines("cuisines"));

		return cuisineModel;
	}

}
