package com.marcusscalet.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.api.v1.assembler.UserModelAssembler;
import com.marcusscalet.algafood.api.v1.model.UserModel;
import com.marcusscalet.algafood.api.v1.openapi.controller.RestaurantUserControllerOpenApi;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/accountable")
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
		
		CollectionModel<UserModel> usersModel = userModelAssembler
	            .toCollectionModel(restaurant.getAccountable())
	                .removeLinks()
	                .add(algaLinks.linkToRestaurantAccountables(restaurantId))
	                .add(algaLinks.linkToRestaurantAccountableAssociate(restaurantId, "associate"));
		
		 usersModel.getContent().stream().forEach(userModel -> {
			 userModel.add(algaLinks.linkToRestaurantAccountableDisassociate(
					 restaurantId, userModel.getId(), "disassociate"));
		 });
		 
		 return usersModel;
	}
	
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associateUser(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantRegistrationService.associateUser(restaurantId, userId);
	    
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> disassociateUser(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantRegistrationService.disassociateUser(restaurantId, userId);
	    
		return ResponseEntity.noContent().build();
	}
	
}
