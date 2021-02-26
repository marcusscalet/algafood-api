package com.marcusscalet.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.marcusscalet.algafood.api.v1.model.OrderSummaryModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("OrdersSummaryModel")
public class OrdersSummaryModelOpenApi {
	
	private OrdersSummaryEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;

	@ApiModel("OrdersSummaryEmbeddedModel")
	@Data
	public class OrdersSummaryEmbeddedModelOpenApi {

		private List<OrderSummaryModel> pedidos;

	}
}