package com.marcusscalet.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.model.Order;

@Service
public class OrderStatusService {

	@Autowired
	private OrderService orderRegistrationService;

	@Transactional
	public void acceptStatus(String orderCode) {
		Order order = orderRegistrationService.searchOrFail(orderCode);

		order.acceptOrder();
	}

	@Transactional
	public void deliveredStatus(String orderCode) {
		Order order = orderRegistrationService.searchOrFail(orderCode);

		order.deliverOrder();
	}

	@Transactional
	public void canceledStatus(String orderCode) {
		Order order = orderRegistrationService.searchOrFail(orderCode);

		order.cancelOrder();
	}
}
