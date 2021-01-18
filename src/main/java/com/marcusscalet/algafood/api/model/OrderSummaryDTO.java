package com.marcusscalet.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.marcusscalet.algafood.domain.model.StatusOrder;

import lombok.Getter;
import lombok.Setter;

@JsonFilter("orderFilter")
@Getter
@Setter
public class OrderSummaryDTO {

	private String code;
	private BigDecimal subtotal;
	private BigDecimal shippingFee;
	private BigDecimal totalCost;
	private StatusOrder status;
	private OffsetDateTime creationDate;
	private RestaurantSummaryDTO restaurant;
	private UserDTO client;
}
