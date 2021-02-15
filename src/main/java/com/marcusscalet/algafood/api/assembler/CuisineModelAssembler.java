package com.marcusscalet.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.controller.CuisineController;
import com.marcusscalet.algafood.api.model.CuisineModel;
import com.marcusscalet.algafood.domain.model.Cuisine;

@Component
public class CuisineModelAssembler extends RepresentationModelAssemblerSupport<Cuisine, CuisineModel> {

	@Autowired
    private ModelMapper modelMapper;
    
	public CuisineModelAssembler() {
		super(CuisineController.class, CuisineModel.class);
	}

	@Override
    public CuisineModel toModel(Cuisine cuisine) {
		CuisineModel cuisineModel = createModelWithId(cuisine.getId(), cuisine);
		modelMapper.map(cuisine, cuisineModel);
		
		cuisineModel.add(linkTo(CuisineController.class).withRel("cuisines"));
		
        return cuisineModel;
    }
    
     	
}
