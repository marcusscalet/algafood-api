package com.marcusscalet.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.model.Order;
import com.marcusscalet.algafood.domain.service.SendEmailService.Message;

@Service
public class OrderStatusService {

	@Autowired
	private OrderService orderRegistrationService;

	@Autowired
	private SendEmailService sendEmail;
	
	@Transactional
	public void confirmedStatus(String orderCode) {
		Order order = orderRegistrationService.searchOrFail(orderCode);
		order.confirm();
		
		var message = Message.builder()
				.topic(order.getRestaurant().getName() + " - Pedido Confirmado")
				.body("order-accepted.html")
				.variable("order", order)
				.recipient(order.getClient().getEmail())
				.build();
		
		sendEmail.send(message);
	}

	@Transactional
	public void deliveredStatus(String orderCode) {
		Order order = orderRegistrationService.searchOrFail(orderCode);

		order.deliver();
	}

	@Transactional
	public void canceledStatus(String orderCode) {
		Order order = orderRegistrationService.searchOrFail(orderCode);

		order.cancel();
	}
}
