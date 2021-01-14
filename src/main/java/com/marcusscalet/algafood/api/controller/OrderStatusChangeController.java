package com.marcusscalet.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.domain.service.OrderStatusChangeService;

@RestController
@RequestMapping(value = "/order/{orderId}")
public class OrderStatusChangeController {

	@Autowired
	private OrderStatusChangeService orderStatusChangeService;

	@PutMapping("/accept")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void acceptStatus(@PathVariable Long orderId) {
		orderStatusChangeService.acceptStatus(orderId);
	}
}
