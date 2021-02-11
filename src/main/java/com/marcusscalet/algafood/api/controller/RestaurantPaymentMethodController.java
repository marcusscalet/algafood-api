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

import com.marcusscalet.algafood.api.assembler.PaymentMethodModelAssembler;
import com.marcusscalet.algafood.api.model.PaymentMethodModel;
import com.marcusscalet.algafood.api.openapi.controller.RestaurantPaymentMethodControllerOpenApi;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/payment-method")
public class RestaurantPaymentMethodController implements RestaurantPaymentMethodControllerOpenApi{

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private PaymentMethodModelAssembler paymentMethodModelAssembler;

	@GetMapping
	public List<PaymentMethodModel> listAll(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);

		return paymentMethodModelAssembler.toCollectionModel(restaurant.getPaymentMethod());
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