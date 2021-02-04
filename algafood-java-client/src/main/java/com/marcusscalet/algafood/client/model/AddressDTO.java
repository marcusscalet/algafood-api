package com.marcusscalet.algafood.client.model;

import lombok.Data;

@Data
public class AddressDTO {

	private String zipCode;

	private String street;

	private String number;

	private String neighborhood;

	private CityResumeDTO city;

}
