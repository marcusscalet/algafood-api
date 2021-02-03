package com.marcusscalet.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.marcusscalet.algafood.domain.event.OrderCanceledEvent;
import com.marcusscalet.algafood.domain.model.Order;
import com.marcusscalet.algafood.domain.service.SendEmailService;
import com.marcusscalet.algafood.domain.service.SendEmailService.Message;

@Component
public class ClientNotificationOrderCanceledListener {

	@Autowired
	private SendEmailService sendEmailService;
	
	@TransactionalEventListener
	public void whenOrderCanceled(OrderCanceledEvent event) {
		Order order = event.getOrder();
		
		var message = Message.builder()
				.topic(order.getRestaurant().getName() + " - Pedido Cancelado")
				.body("order-canceled.html")
				.variable("order", order)
				.recipient(order.getClient().getEmail())
				.build();
		
		sendEmailService.send(message);
	}
}
