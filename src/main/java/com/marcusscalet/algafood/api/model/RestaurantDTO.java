package com.marcusscalet.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantDTO {

	private Long id;
	
	private String name;
	private BigDecimal shippingFee;
	private CuisineDTO cuisine;
	private Boolean active;
}
