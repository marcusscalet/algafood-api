package com.marcusscalet.algafood.api.assembler;

import com.marcusscalet.algafood.api.model.input.RestaurantInput;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.model.Restaurant;

public class RestaurantModelDisassembler {

	public Restaurant toDomainObject(RestaurantInput restaurantInput) {
		Cuisine cuisine = new Cuisine();
		cuisine.setId(restaurantInput.getCuisine().getId());

		Restaurant restaurant = new Restaurant();
		restaurant.setName(restaurant.getName());
		restaurant.setShippingFee(restaurant.getShippingFee());
		restaurant.setCuisine(cuisine);

		return restaurant;
	}
}
