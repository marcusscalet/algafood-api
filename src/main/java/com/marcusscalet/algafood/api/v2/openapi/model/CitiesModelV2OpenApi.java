package com.marcusscalet.algafood.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.marcusscalet.algafood.api.v2.model.CityModelV2;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CitiesModel")
public class CitiesModelV2OpenApi {

	private CityEmbeddedModelOpenApi _embedded;

	private Links _links;
	
	@Data
	@ApiModel("CitiesEmbeddedModel")
	public class CityEmbeddedModelOpenApi {

		private List<CityModelV2> cities;
	}
}
