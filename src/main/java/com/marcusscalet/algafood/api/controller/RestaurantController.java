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
import com.marcusscalet.algafood.api.assembler.RestaurantDTOAssembler;
import com.marcusscalet.algafood.api.assembler.RestaurantInputDisassembler;
import com.marcusscalet.algafood.api.model.RestaurantDTO;
import com.marcusscalet.algafood.api.model.input.RestaurantInput;
import com.marcusscalet.algafood.api.model.view.RestaurantView;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.CityNotFoundException;
import com.marcusscalet.algafood.domain.exception.CuisineNotFoundException;
import com.marcusscalet.algafood.domain.exception.RestaurantNotFoundException;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private RestaurantDTOAssembler restaurantDTOAssembler;

	@Autowired
	private RestaurantInputDisassembler restaurantInputDisassembler;
	
//	@GetMapping
//	public MappingJacksonValue listAll(@RequestParam(required=false) String view) {
//		
//		List<Restaurant> restaurants = restaurantRegistrationService.listAll();
//		List<RestaurantDTO> restaurantsDTO = restaurantDTOAssembler.toCollectionDTO(restaurants);
//		
//		MappingJacksonValue restaurantsWrapper = new MappingJacksonValue(restaurantsDTO);
//		
//		if("only-name".equals(view)) {
//		restaurantsWrapper.setSerializationView(RestaurantView.OnlyName.class);
//		} else if("whole".equals(view)) {
//			restaurantsWrapper.setSerializationView(null);
//		}
//		return restaurantsWrapper;
//	}
	
//	@GetMapping
//	public List<RestaurantDTO> listAll() {
//		return restaurantDTOAssembler.toCollectionDTO(restaurantRegistrationService.listAll());
//	}
//	
	@JsonView(RestaurantView.Summary.class)
	@GetMapping
	public List<RestaurantDTO> listAllSummary() {
		return restaurantDTOAssembler.toCollectionDTO(restaurantRegistrationService.listAll());
	}
	
	@JsonView(RestaurantView.OnlyName.class)
	@GetMapping(params = "view=only-name")
	public List<RestaurantDTO> listAllByName() {
		return restaurantDTOAssembler.toCollectionDTO(restaurantRegistrationService.listAll());
	}

	@GetMapping("/{restaurantId}")
	public RestaurantDTO find(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);

		return restaurantDTOAssembler.toDTO(restaurant);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantDTO add(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);

			return restaurantDTOAssembler.toDTO(restaurantRegistrationService.saveRestaurant(restaurant));
		} catch (CuisineNotFoundException | CityNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@PutMapping("/{restaurantId}")
	public RestaurantDTO update(@PathVariable Long restaurantId, @Valid @RequestBody RestaurantInput restaurantInput) {
		try {

			Restaurant currentRestaurant = restaurantRegistrationService.searchOrFail(restaurantId);

			restaurantInputDisassembler.copyToDomainObject(restaurantInput, currentRestaurant);

			return restaurantDTOAssembler.toDTO(restaurantRegistrationService.saveRestaurant(currentRestaurant));
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