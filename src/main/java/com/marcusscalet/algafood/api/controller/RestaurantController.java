package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.assembler.RestaurantModelAssembler;
import com.marcusscalet.algafood.api.assembler.RestaurantModelDisassembler;
import com.marcusscalet.algafood.api.model.RestaurantDTO;
import com.marcusscalet.algafood.api.model.input.RestaurantInput;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.CuisineNotFoundException;
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
	private RestaurantModelAssembler restaurantModelAssembler;

	@Autowired
	private RestaurantModelDisassembler restaurantModelDisassembler;

	@GetMapping
	public List<RestaurantDTO> listAll() {
		return restaurantModelAssembler.toCollectionDTO(restaurantRepository.findAll());
	}

	@GetMapping("/{restaurantId}")
	public RestaurantDTO find(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);

		return restaurantModelAssembler.toDTO(restaurant);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantDTO add(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = restaurantModelDisassembler.toDomainObject(restaurantInput);

			return restaurantModelAssembler.toDTO(restaurantRegistrationService.saveRestaurant(restaurant));
		} catch (CuisineNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{restaurantId}")
	public RestaurantDTO update(@PathVariable Long restaurantId, @Valid @RequestBody RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = restaurantModelDisassembler.toDomainObject(restaurantInput);

			System.out.println(restaurant);
			Restaurant currentRestaurant = restaurantRegistrationService.searchOrFail(restaurantId);

			// excluir os campos mencionados na hora de fazer o copy
			BeanUtils.copyProperties(restaurant, currentRestaurant, "id", "paymentMethod", "address",
					"registrationDate", "products");

			return restaurantModelAssembler.toDTO(restaurantRegistrationService.saveRestaurant(currentRestaurant));
		} catch (CuisineNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}

	}


}