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

import com.marcusscalet.algafood.api.assembler.PaymentMethodDTOAssembler;
import com.marcusscalet.algafood.api.model.PaymentMethodDTO;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/payment-method")
public class RestaurantPaymentMethodController {

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private PaymentMethodDTOAssembler paymentMethodDTOAssembler;

	@GetMapping
	public List<PaymentMethodDTO> listAll(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);

		return paymentMethodDTOAssembler.toCollectionDTO(restaurant.getPaymentMethod());
	}

	@PutMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void attach(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantRegistrationService.associatePaymentMethod(restaurantId, paymentMethodId);
	}

	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void detach(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantRegistrationService.disassociatePaymentMethod(restaurantId, paymentMethodId);
	}

}