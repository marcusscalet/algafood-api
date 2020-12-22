package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.RestaurantModel;
import com.marcusscalet.algafood.domain.model.Restaurant;

@Component
public class RestaurantModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public RestaurantModel toDTO(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantModel.class);
	}

	public List<RestaurantModel> toCollectionDTO(List<Restaurant> restaurants) {
		return restaurants.stream().map(restaurant -> toDTO(restaurant)).collect(Collectors.toList());
	}

}
