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

import com.marcusscalet.algafood.api.openapi.controller.RestaurantPaymentMethodControllerOpenApi;
import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.api.v1.assembler.PaymentMethodModelAssembler;
import com.marcusscalet.algafood.api.v1.model.PaymentMethodModel;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/payment-methods")
public class RestaurantPaymentMethodController implements RestaurantPaymentMethodControllerOpenApi{

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private PaymentMethodModelAssembler paymentMethodModelAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Override
	@GetMapping
	public CollectionModel<PaymentMethodModel> listAll(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);

		CollectionModel<PaymentMethodModel> paymentMethodsModel 
			= paymentMethodModelAssembler.toCollectionModel(restaurant.getPaymentMethod())
				.removeLinks()
				.add(algaLinks.linkToRestaurantPaymentMethods(restaurantId))
				.add(algaLinks.linkToRestaurantPaymentMethodAttach(restaurantId, "attach"));
		
		paymentMethodsModel.getContent().forEach(paymentMethodModel -> {
			paymentMethodModel.add(algaLinks.linkToRestaurantPaymentMethodDetach(
					restaurantId, paymentMethodModel.getId(), "detach"));
		});
		
		return paymentMethodsModel;
	}

	@PutMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> attach(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantRegistrationService.associatePaymentMethod(restaurantId, paymentMethodId);
		
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> detach(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantRegistrationService.disassociatePaymentMethod(restaurantId, paymentMethodId);
		
		return ResponseEntity.noContent().build();
	}

}