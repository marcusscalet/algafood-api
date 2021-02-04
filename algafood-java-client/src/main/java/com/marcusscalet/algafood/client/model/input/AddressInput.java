package com.marcusscalet.algafood.client.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressInput {
	
	private String zipCode;

	private String street;

	private String number;

	private String neighborhood;

	private CityInput city;
}
