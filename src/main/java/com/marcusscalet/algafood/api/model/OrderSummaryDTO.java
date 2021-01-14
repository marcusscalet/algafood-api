package com.marcusscalet.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.marcusscalet.algafood.domain.model.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryDTO {

	private Long id;
	private BigDecimal subtotal;
	private BigDecimal shippingFee;
	private BigDecimal totalCost;
	private OrderStatus status;
	private OffsetDateTime creationDate;
	private RestaurantSummaryDTO restaurant;
	private UserDTO client;
}
