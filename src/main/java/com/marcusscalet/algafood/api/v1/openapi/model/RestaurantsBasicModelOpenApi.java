package com.marcusscalet.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.marcusscalet.algafood.api.v1.model.RestaurantBasicModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("RestaurantsBasicModel")
@Data
public class RestaurantsBasicModelOpenApi {
	
	private RestaurantsEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("RestaurantsEmbeddedModel")
	@Data
	public class RestaurantsEmbeddedModelOpenApi {

		private List<RestaurantBasicModel> restaurants;

	}

}