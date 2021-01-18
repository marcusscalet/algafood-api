package com.marcusscalet.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.marcusscalet.algafood.api.model.view.RestaurantView;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CuisineDTO {

	@JsonView(RestaurantView.Summary.class)
	private Long id;
	
	@JsonView(RestaurantView.Summary.class)
	private String name;
}
