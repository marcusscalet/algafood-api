package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.marcusscalet.algafood.api.assembler.RestaurantModelAssembler;
import com.marcusscalet.algafood.api.assembler.RestaurantInputDisassembler;
import com.marcusscalet.algafood.api.model.RestaurantModel;
import com.marcusscalet.algafood.api.model.input.RestaurantInput;
import com.marcusscalet.algafood.api.model.view.RestaurantView;
import com.marcusscalet.algafood.api.openapi.controller.RestaurantControllerOpenApi;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.CityNotFoundException;
import com.marcusscalet.algafood.domain.exception.CuisineNotFoundException;
import com.marcusscalet.algafood.domain.exception.RestaurantNotFoundException;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController implements RestaurantControllerOpenApi{

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private RestaurantModelAssembler restaurantModelAssembler;

	@Autowired
	private RestaurantInputDisassembler restaurantInputDisassembler;
	
	@JsonView(RestaurantView.Summary.class)
	@GetMapping
	public List<RestaurantModel> listAllSummary() {
		return restaurantModelAssembler.toCollectionModel(restaurantRegistrationService.listAll());
	}
	
	@JsonView(RestaurantView.OnlyName.class)
	@GetMapping(params = "view=only-name")
	public List<RestaurantModel> listAllByName() {
		return restaurantModelAssembler.toCollectionModel(restaurantRegistrationService.listAll());
	}

	@GetMapping("/{restaurantId}")
	public RestaurantModel find(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);

		return restaurantModelAssembler.toModel(restaurant);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantModel add(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);

			return restaurantModelAssembler.toModel(restaurantRegistrationService.saveRestaurant(restaurant));
		} catch (CuisineNotFoundException | CityNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{restaurantId}")
	public RestaurantModel update(@PathVariable Long restaurantId, @Valid @RequestBody RestaurantInput restaurantInput) {
		try {

			Restaurant currentRestaurant = restaurantRegistrationService.searchOrFail(restaurantId);

			restaurantInputDisassembler.copyToDomainObject(restaurantInput, currentRestaurant);

			return restaurantModelAssembler.toModel(restaurantRegistrationService.saveRestaurant(currentRestaurant));
		} catch (CuisineNotFoundException | CityNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}

	}

	@PutMapping("/{restaurantId}/open")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void openRestaurant(@PathVariable Long restaurantId) {
		restaurantRegistrationService.open(restaurantId);
	}

	@PutMapping("/{restaurantId}/close")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void closeRestaurant(@PathVariable Long restaurantId) {
		restaurantRegistrationService.close(restaurantId);
	}

	@PutMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activate(@PathVariable Long restaurantId) {
		restaurantRegistrationService.activate(restaurantId);
	}

	@DeleteMapping("/{restaurantId}/inactive")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inactivate(@PathVariable Long restaurantId) {
		restaurantRegistrationService.inactivate(restaurantId);
	}

	@PutMapping("/activation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void multipleActivation(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantRegistrationService.activate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@DeleteMapping("/activation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void multipleInactivation(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantRegistrationService.inactivate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

}