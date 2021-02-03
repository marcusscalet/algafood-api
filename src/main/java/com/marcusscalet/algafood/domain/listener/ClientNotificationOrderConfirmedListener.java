package com.marcusscalet.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.marcusscalet.algafood.domain.event.OrderConfirmedEvent;
import com.marcusscalet.algafood.domain.model.Order;
import com.marcusscalet.algafood.domain.service.SendEmailService;
import com.marcusscalet.algafood.domain.service.SendEmailService.Message;

@Component
public class ClientNotificationOrderConfirmedListener {

	@Autowired
	private SendEmailService sendEmailService;
	
	@TransactionalEventListener
	public void whenOrderConfirmed(OrderConfirmedEvent event) {
		Order order = event.getOrder();
		
		var message = Message.builder()
				.topic(order.getRestaurant().getName() + " - Pedido Confirmado")
				.body("order-accepted.html")
				.variable("order", order)
				.recipient(order.getClient().getEmail())
				.build();
		
		sendEmailService.send(message);
	}
}
