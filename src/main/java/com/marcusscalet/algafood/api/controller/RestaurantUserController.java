package com.marcusscalet.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.AlgaLinks;
import com.marcusscalet.algafood.api.assembler.UserModelAssembler;
import com.marcusscalet.algafood.api.model.UserModel;
import com.marcusscalet.algafood.api.openapi.controller.RestaurantUserControllerOpenApi;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/accountable")
public class RestaurantUserController implements RestaurantUserControllerOpenApi {

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private UserModelAssembler userModelAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public CollectionModel<UserModel> listAll(@PathVariable Long restaurantId){
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);
		
		return userModelAssembler.toCollectionModel(restaurant.getAccountable())
				.removeLinks()
				.add(algaLinks.linkToRestaurantAccountables(restaurantId));
	}
	
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associateUser(@PathVariable Long userId, @PathVariable Long restaurantId) {
		restaurantRegistrationService.associateUser(restaurantId, userId);
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociateUser(@PathVariable Long userId, @PathVariable Long restaurantId) {
		restaurantRegistrationService.disassociateUser(restaurantId, userId);
	}
	
}
