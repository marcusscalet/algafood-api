package com.marcusscalet.algafood.domain.event;

import com.marcusscalet.algafood.domain.model.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCanceledEvent {

	private Order order;
}
