package com.marcusscalet.algafood.client.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestaurantSummaryDTO {

	private Long id;
	private String name;
	private BigDecimal shippingFee;
	private CuisineDTO cuisine;
}
