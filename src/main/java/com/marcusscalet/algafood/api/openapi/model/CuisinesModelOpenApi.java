package com.marcusscalet.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.marcusscalet.algafood.api.v1.model.CuisineModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CuisinesModel")
@Getter
@Setter
public class CuisinesModelOpenApi{

	private CuisinesEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@Data
	@ApiModel("CuisinesEmbeddedModel")
	public class CuisinesEmbeddedModelOpenApi{
		
		private List<CuisineModel> cuisines;
	}
}
