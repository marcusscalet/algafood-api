package com.marcusscalet.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.repository.RestaurantRepository;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;
@RestController
@RequestMapping(value = "/restaurantes")
public class RestaurantController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@GetMapping
	public List<Restaurant> listAll() {
		return restaurantRepository.findAll();
	}

	@GetMapping("/{restaurantId}")
	public ResponseEntity<Restaurant> find(@PathVariable Long restaurantId) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

		if (restaurant.isPresent()) {
			return ResponseEntity.ok(restaurant.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Restaurant restaurant) {
		try {
			restaurant = restaurantRegistrationService.saveRestaurant(restaurant);

			return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{restaurantId}")
	public ResponseEntity<?> update(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
		try {
			Restaurant currentRestaurant = restaurantRepository.findById(restaurantId).orElse(null);

			if (currentRestaurant != null) {
				BeanUtils.copyProperties(restaurant, currentRestaurant, "id");

				currentRestaurant = restaurantRegistrationService.saveRestaurant(currentRestaurant);
				return ResponseEntity.ok(currentRestaurant);
			}

			return ResponseEntity.notFound().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PatchMapping("/{restaurantId}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long restaurantId, @RequestBody Map<String, Object> fields) {
		Restaurant currentRestaurant = restaurantRepository.findById(restaurantId).orElse(null);

		if (currentRestaurant == null) {
			return ResponseEntity.notFound().build();
		}

		merge(fields, currentRestaurant);

		return update(restaurantId, currentRestaurant);
	}

	private void merge(Map<String, Object> mapData, Restaurant restaurantData) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurant restaurant = objectMapper.convertValue(mapData, Restaurant.class);

		mapData.forEach((propertyName, propertyValue) -> {
			Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
			field.setAccessible(true);

			Object newValue = ReflectionUtils.getField(field, restaurant);

//			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

			ReflectionUtils.setField(field, restaurantData, newValue);
		});
	}

}