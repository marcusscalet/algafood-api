package com.marcusscalet.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.api.v2.controller.CityControllerV2;
import com.marcusscalet.algafood.api.v2.model.CityModelV2;
import com.marcusscalet.algafood.domain.model.City;

@Component
public class CityModelAssemblerV2 extends RepresentationModelAssemblerSupport<City, CityModelV2>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public CityModelAssemblerV2() {
		super(CityControllerV2.class, CityModelV2.class);
	}
	
	@Override
	public CityModelV2 toModel(City city) {
		CityModelV2 cityModel = createModelWithId(city.getId(), city);
		
		modelMapper.map(city, cityModel);
		
		cityModel.add(algaLinks.linkToCities("cities"));
		
		return cityModel;
	}
	
	@Override
	public CollectionModel<CityModelV2> toCollectionModel(Iterable<? extends City> entities) {
		return super .toCollectionModel(entities)
				.add(algaLinks.linkToCities());
	}

}
