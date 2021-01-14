package com.marcusscalet.algafood.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.model.Order;
import com.marcusscalet.algafood.domain.model.OrderStatus;

@Service
public class OrderStatusChangeService {

	@Autowired
	private IssuePurchaseOrderService orderRegistrationService;
	
	@Transactional
	public void acceptStatus(Long orderId) {
		Order order = orderRegistrationService.searchOrFail(orderId);
		
		if(!order.getStatus().equals(OrderStatus.CREATED)) {
			throw new BusinessException(String.format("Status do pedido %d não pode ser alterado para de %s para %s", 
					order.getId(), 
					order.getStatus().getDescription(),
					OrderStatus.ACCEPTED));
		}
		
		order.setStatus(OrderStatus.ACCEPTED);
		order.setConfirmationDate(OffsetDateTime.now());
	}
}
