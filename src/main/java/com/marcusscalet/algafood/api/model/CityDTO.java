package com.marcusscalet.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO {

	private Long id;
	private String name;
	private StateDTO state;
}
