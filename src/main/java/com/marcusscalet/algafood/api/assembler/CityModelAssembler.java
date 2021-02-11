package com.marcusscalet.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.controller.CityController;
import com.marcusscalet.algafood.api.controller.StateController;
import com.marcusscalet.algafood.api.model.CityModel;
import com.marcusscalet.algafood.domain.model.City;

@Component
public class CityModelAssembler extends RepresentationModelAssemblerSupport<City, CityModel>{

	@Autowired
	private ModelMapper modelMapper;
	
	public CityModelAssembler() {
		super(CityController.class, CityModel.class);
	}
	
	public CityModel toModel(City city) {
		CityModel cityModel = createModelWithId(city.getId(), city);
		modelMapper.map(city, cityModel);
		
//		CityModel cityModel =  modelMapper.map(city, CityModel.class);
		
//		cityModel.add(linkTo(methodOn(CityController.class)
//				.find(cityModel.getId())).withSelfRel());
			
		cityModel.add(linkTo(methodOn(CityController.class)
				.listAll()).withRel("cities"));
		
		cityModel.getState().add(linkTo(methodOn(StateController.class)
				.find(cityModel.getState().getId())).withSelfRel());
		
		return cityModel;
	}
	
	@Override
	public CollectionModel<CityModel> toCollectionModel(Iterable<? extends City> entities) {
		return super .toCollectionModel(entities)
				.add(linkTo(CityController.class).withSelfRel());
	}

}
