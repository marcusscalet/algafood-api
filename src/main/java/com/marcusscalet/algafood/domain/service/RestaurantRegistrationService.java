package com.marcusscalet.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcusscalet.algafood.domain.exception.RestaurantNotFoundException;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restauranteRepository;

	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;

	@Transactional
	public Restaurant saveRestaurant(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();

		Cuisine cuisine = cuisineRegistrationService.searchOrFail(cuisineId);

		restaurant.setCuisine(cuisine);

		return restauranteRepository.save(restaurant);
	}

	@Transactional
	public void activate(Long restaurantId) {
		Restaurant currentRestaurant = searchOrFail(restaurantId);

		currentRestaurant.activate();
	}
	
	@Transactional
	public void inactivate(Long restaurantId) {
		Restaurant currentRestaurant = searchOrFail(restaurantId);

		currentRestaurant.inactivate();
	}

	public Restaurant searchOrFail(Long restaurantId) {
		return restauranteRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}
}