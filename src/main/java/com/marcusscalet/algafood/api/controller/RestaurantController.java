package com.marcusscalet.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusscalet.algafood.core.validation.ValidationException;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.CuisineNotFoundException;
import com.marcusscalet.algafood.domain.exception.RestaurantNotFoundException;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.repository.RestaurantRepository;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private SmartValidator validator;
	
	@GetMapping
	public List<Restaurant> listAll() {
		return restaurantRepository.findAll();
	}

	@GetMapping("/{restaurantId}")
	public Restaurant find(@PathVariable Long restaurantId) {
		return restaurantRegistrationService.searchOrFail(restaurantId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant add(@RequestBody @Valid Restaurant restaurant) {
		try {
			return restaurantRegistrationService.saveRestaurant(restaurant);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{restaurantId}")
	public Restaurant update(@PathVariable Long restaurantId, @Valid @RequestBody Restaurant restaurant) {

		try {
			Restaurant currentRestaurant = restaurantRegistrationService.searchOrFail(restaurantId);

			// excluir os campos mencionados na hora de fazer o copy
			BeanUtils.copyProperties(restaurant, currentRestaurant, "id", "paymentMethod", "address",
					"registrationDate", "products");

			return restaurantRegistrationService.saveRestaurant(currentRestaurant);
		} catch (CuisineNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}

	}

	@PatchMapping("/{restaurantId}")
	public Restaurant partialUpdate(@PathVariable Long restaurantId, @RequestBody Map<String, Object> fields, HttpServletRequest request) {
		Restaurant currentRestaurant = restaurantRegistrationService.searchOrFail(restaurantId);

		merge(fields, currentRestaurant, request);
		validate(currentRestaurant, "restaurant");

		return update(restaurantId, currentRestaurant);
	}

	private void validate(Restaurant restaurant, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant, objectName);
		
		validator.validate(restaurant, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult);
		}
	}

	private void merge(Map<String, Object> mapData, Restaurant restaurantData, HttpServletRequest request) {
		
		ServletServerHttpRequest servletHttpRequest = new ServletServerHttpRequest(request);
		
		try {
		ObjectMapper objectMapper = new ObjectMapper();
		
		//Configurando manualmente para falhar em tentativas de passar dados em propriedades que estão com @JsonIgnore
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		
		Restaurant restaurant = objectMapper.convertValue(mapData, Restaurant.class);

		mapData.forEach((propertyName, propertyValue) -> {
			Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
			field.setAccessible(true);

			Object newValue = ReflectionUtils.getField(field, restaurant);

//			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

			ReflectionUtils.setField(field, restaurantData, newValue);
		});
		}catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletHttpRequest);
		}
	}

}