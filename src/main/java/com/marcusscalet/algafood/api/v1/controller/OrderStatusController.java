package com.marcusscalet.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.openapi.controller.OrderStatusControllerOpenApi;
import com.marcusscalet.algafood.domain.service.OrderStatusService;

@RestController
@RequestMapping(path = "/v1/orders/{orderCode}")
public class OrderStatusController implements OrderStatusControllerOpenApi {

	@Autowired
	private OrderStatusService orderStatusChangeService;

	@PutMapping("/confirmed")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmedStatus(@PathVariable String orderCode) {
		orderStatusChangeService.confirmedStatus(orderCode);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/delivered")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deliveredStatus(@PathVariable String orderCode) {
		orderStatusChangeService.deliveredStatus(orderCode);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/cancelled")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelledStatus(@PathVariable String orderCode) {
		orderStatusChangeService.canceledStatus(orderCode);

		return ResponseEntity.noContent().build();
	}

}
