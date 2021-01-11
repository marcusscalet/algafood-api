package com.marcusscalet.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.assembler.UserDTOAssembler;
import com.marcusscalet.algafood.api.model.UserDTO;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/accountable")
public class RestaurantUserController {

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private UserDTOAssembler userDTOAssembler;
	
	@GetMapping
	public List<UserDTO> listAll(@PathVariable Long restaurantId){
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);
		
		return userDTOAssembler.toCollectionDTO(restaurant.getAccountable());
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
