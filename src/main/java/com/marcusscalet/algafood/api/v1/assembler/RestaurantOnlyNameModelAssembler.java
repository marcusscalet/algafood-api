package com.marcusscalet.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.api.v1.controller.RestaurantController;
import com.marcusscalet.algafood.api.v1.model.RestaurantOnlyNameModel;
import com.marcusscalet.algafood.domain.model.Restaurant;

@Component
public class RestaurantOnlyNameModelAssembler
		extends RepresentationModelAssemblerSupport<Restaurant, RestaurantOnlyNameModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public RestaurantOnlyNameModelAssembler() {
		super(RestaurantController.class, RestaurantOnlyNameModel.class);
	}

	@Override
	public RestaurantOnlyNameModel toModel(Restaurant restaurant) {
		RestaurantOnlyNameModel restaurantModel = createModelWithId(restaurant.getId(), restaurant);

		modelMapper.map(restaurant, restaurantModel);

		restaurantModel.add(algaLinks.linkToRestaurants("restaurants"));

		return restaurantModel;
	}

	@Override
	public CollectionModel<RestaurantOnlyNameModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToRestaurants());
	}
}