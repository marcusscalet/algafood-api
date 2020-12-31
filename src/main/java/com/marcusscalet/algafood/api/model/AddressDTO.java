package com.marcusscalet.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

	private String zipCode;

	private String street;

	private String number;

	private String neighborhood;

	private CityResumeDTO city;

}
