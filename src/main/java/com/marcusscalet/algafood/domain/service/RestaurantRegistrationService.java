package com.marcusscalet.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.repository.CuisineRepository;
import com.marcusscalet.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restauranteRepository;

	@Autowired
	private CuisineRepository cuisineRepository;

	public Restaurant saveRestaurant(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();

		Cuisine cuisine = cuisineRepository.findById(cuisineId)
				.orElseThrow(() -> new EntityNotFoundException(
				String.format("Não existe cadastro de cozinha com código %d", cuisineId)));

		restaurant.setCuisine(cuisine);

		return restauranteRepository.save(restaurant);
	}
}