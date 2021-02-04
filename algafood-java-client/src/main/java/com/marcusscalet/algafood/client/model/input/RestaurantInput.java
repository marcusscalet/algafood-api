package com.marcusscalet.algafood.client.model.input;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantInput {

	private String name;
	
	private BigDecimal shippingFee;
	
	private CuisineInput cuisine;
	
	private AddressInput address;
}
