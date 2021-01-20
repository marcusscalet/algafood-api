package com.marcusscalet.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.domain.service.OrderStatusService;

@RestController
@RequestMapping(value = "/orders/{orderCode}")
public class OrderStatusController {

	@Autowired
	private OrderStatusService orderStatusChangeService;

	@PutMapping("/accept")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void acceptStatus(@PathVariable String orderCode) {
		orderStatusChangeService.acceptStatus(orderCode);
	}
	
	@PutMapping("/delivered")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deliveredStatus(@PathVariable String orderCode) {
		orderStatusChangeService.deliveredStatus(orderCode);
	}
	
	@PutMapping("/canceled")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelledStatus(@PathVariable String orderCode) {
		orderStatusChangeService.canceledStatus(orderCode);
	}

}