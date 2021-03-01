package com.marcusscalet.algafood.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.marcusscalet.algafood.api.v2.model.CuisineModelV2;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CuisinesModel")
@Getter
@Setter
public class CuisinesModelV2OpenApi{

	private CuisinesEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelV2OpenApi page;
	
	@Data
	@ApiModel("CuisinesEmbeddedModel")
	public class CuisinesEmbeddedModelOpenApi{
		
		private List<CuisineModelV2> cuisines;
	}
}
