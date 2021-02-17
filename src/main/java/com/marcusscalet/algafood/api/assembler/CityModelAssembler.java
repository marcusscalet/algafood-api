package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.AlgaLinks;
import com.marcusscalet.algafood.api.controller.CityController;
import com.marcusscalet.algafood.api.model.CityModel;
import com.marcusscalet.algafood.domain.model.City;

@Component
public class CityModelAssembler extends RepresentationModelAssemblerSupport<City, CityModel>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public CityModelAssembler() {
		super(CityController.class, CityModel.class);
	}
	
	@Override
	public CityModel toModel(City city) {
		CityModel cityModel = createModelWithId(city.getId(), city);
		
		modelMapper.map(city, cityModel);
		
		cityModel.add(algaLinks.linkToCities("cities"));
		
		cityModel.getState().add(algaLinks.linkToState(cityModel.getState().getId()));
		
		return cityModel;
	}
	
	@Override
	public CollectionModel<CityModel> toCollectionModel(Iterable<? extends City> entities) {
		return super .toCollectionModel(entities)
				.add(algaLinks.linkToCities());
	}

}
