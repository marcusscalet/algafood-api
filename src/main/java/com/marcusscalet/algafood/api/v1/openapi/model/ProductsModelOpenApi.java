package com.marcusscalet.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.marcusscalet.algafood.api.v1.model.ProductModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("ProductsModel")
@Data
public class ProductsModelOpenApi {
	
	private ProductsEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("ProductsEmbeddedModel")
	@Data
	public class ProductsEmbeddedModelOpenApi {

		private List<ProductModel> products;

	}
}
