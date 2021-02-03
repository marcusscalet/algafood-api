package com.marcusscalet.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.model.Order;
import com.marcusscalet.algafood.domain.repository.OrderRepository;

@Service
public class OrderStatusService {

	@Autowired
	private OrderService orderRegistrationService;

	@Autowired
	private OrderRepository orderRepository;
	
	@Transactional
	public void confirmedStatus(String orderCode) {
		Order order = orderRegistrationService.searchOrFail(orderCode);
		order.confirm();
		
		orderRepository.save(order);
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
		
		orderRepository.save(order);
	}
}
