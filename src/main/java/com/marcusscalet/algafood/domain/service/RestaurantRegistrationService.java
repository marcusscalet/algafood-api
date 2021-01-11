package com.marcusscalet.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcusscalet.algafood.domain.exception.RestaurantNotFoundException;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.model.PaymentMethod;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restauranteRepository;

	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;

	@Autowired
	private CityRegistrationService cityRegistrationService;

	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;

	public List<Restaurant> listAll() {
		return restauranteRepository.findAll();
	}

	public Restaurant searchOrFail(Long restaurantId) {
		return restauranteRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}

	@Transactional
	public Restaurant saveRestaurant(Restaurant restaurant) {

		Long cuisineId = restaurant.getCuisine().getId();
		Long cityId = restaurant.getAddress().getCity().getId();

		Cuisine cuisine = cuisineRegistrationService.searchOrFail(cuisineId);
		City city = cityRegistrationService.searchOrFail(cityId);

		restaurant.setCuisine(cuisine);
		restaurant.getAddress().setCity(city);

		return restauranteRepository.save(restaurant);
	}
	
	@Transactional
	public void open(Long restaurantId) {
		Restaurant currentRestaurant = searchOrFail(restaurantId);
		currentRestaurant.open();
	}
	
	@Transactional
	public void close(Long restaurantId) {
		Restaurant currentRestaurant = searchOrFail(restaurantId);
		currentRestaurant.close();
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

	@Transactional
	public void associatePaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant currentRestaurant = searchOrFail(restaurantId);
		PaymentMethod currentPaymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);

		currentRestaurant.addPaymentMethod(currentPaymentMethod);
	}

	@Transactional
	public void disassociatePaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant currentRestaurant = searchOrFail(restaurantId);
		PaymentMethod currentPaymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);

		currentRestaurant.removePaymentMethod(currentPaymentMethod);
	}
	
	@Transactional
	public void associateUser(Long restaurantId, Long userId) {
		Restaurant restaurant = searchOrFail(restaurantId);
		User user = userRegistrationService.searchOrFail(userId);
		
		restaurant.addUser(user);
	}
	
	@Transactional
	public void disassociateUser(Long restaurantId, Long userId) {
		Restaurant restaurant = searchOrFail(restaurantId);
		User user = userRegistrationService.searchOrFail(userId);
		
		restaurant.removeUser(user);
	}
	
}