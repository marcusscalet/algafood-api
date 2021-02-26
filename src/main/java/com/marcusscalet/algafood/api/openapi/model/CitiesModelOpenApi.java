package com.marcusscalet.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.marcusscalet.algafood.api.v1.model.CityModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CitiesModel")
public class CitiesModelOpenApi {

	private CityEmbeddedModelOpenApi _embedded;

	private Links _links;
	
	@Data
	@ApiModel("CitiesEmbeddedModel")
	public class CityEmbeddedModelOpenApi {

		private List<CityModel> cities;
	}
}
