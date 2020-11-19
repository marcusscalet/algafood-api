package com.marcusscalet.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	private static final String MSG_RESTAURANT_NOT_FOUND = "Não existe cadastro de restaurante com código %d";

	@Autowired
	private RestaurantRepository restauranteRepository;

	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;

	public Restaurant saveRestaurant(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();

		Cuisine cuisine = cuisineRegistrationService.searchOrFail(cuisineId);
		restaurant.setCuisine(cuisine);

		return restauranteRepository.save(restaurant);
	}

	public Restaurant searchOrFail(Long restaurantId) {
		return restauranteRepository.findById(restaurantId)
				.orElseThrow(() -> new EntityNotFoundException(String.format(MSG_RESTAURANT_NOT_FOUND, restaurantId)));
	}
}