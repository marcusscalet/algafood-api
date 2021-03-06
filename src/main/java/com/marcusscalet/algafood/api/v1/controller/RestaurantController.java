package com.marcusscalet.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.v1.assembler.RestaurantBasicModelAssembler;
import com.marcusscalet.algafood.api.v1.assembler.RestaurantInputDisassembler;
import com.marcusscalet.algafood.api.v1.assembler.RestaurantModelAssembler;
import com.marcusscalet.algafood.api.v1.assembler.RestaurantOnlyNameModelAssembler;
import com.marcusscalet.algafood.api.v1.model.RestaurantBasicModel;
import com.marcusscalet.algafood.api.v1.model.RestaurantModel;
import com.marcusscalet.algafood.api.v1.model.RestaurantOnlyNameModel;
import com.marcusscalet.algafood.api.v1.model.input.RestaurantInput;
import com.marcusscalet.algafood.api.v1.openapi.controller.RestaurantControllerOpenApi;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.CityNotFoundException;
import com.marcusscalet.algafood.domain.exception.CuisineNotFoundException;
import com.marcusscalet.algafood.domain.exception.RestaurantNotFoundException;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(path = "/v1/restaurants")
public class RestaurantController implements RestaurantControllerOpenApi{

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private RestaurantModelAssembler restaurantModelAssembler;

	@Autowired
	private RestaurantInputDisassembler restaurantInputDisassembler;
	
	@Autowired
	private RestaurantBasicModelAssembler restaurantBasicModelAssembler;

	@Autowired
	private RestaurantOnlyNameModelAssembler restaurantOnlyNameModelAssembler;  

	@GetMapping
	public CollectionModel<RestaurantBasicModel> listAll() {
		return restaurantBasicModelAssembler.toCollectionModel(restaurantRegistrationService.listAll());
	}
	
	@GetMapping(params = "view=only-name")
	public CollectionModel<RestaurantOnlyNameModel> listAllByName() {
		return restaurantOnlyNameModelAssembler.toCollectionModel(restaurantRegistrationService.listAll());
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

	@Override
	@PutMapping("/{restaurantId}/open")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> openRestaurant(@PathVariable Long restaurantId) {
		restaurantRegistrationService.open(restaurantId);
		
	    return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/{restaurantId}/close")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> closeRestaurant(@PathVariable Long restaurantId) {
		restaurantRegistrationService.close(restaurantId);
		
		 return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> activate(@PathVariable Long restaurantId) {
		restaurantRegistrationService.activate(restaurantId);
		
	    return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{restaurantId}/inactive")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inactivate(@PathVariable Long restaurantId) {
		restaurantRegistrationService.inactivate(restaurantId);

	    return ResponseEntity.noContent().build();
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

	@DeleteMapping("/inactivation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void multipleInactivation(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantRegistrationService.inactivate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

}