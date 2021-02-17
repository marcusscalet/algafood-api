package com.marcusscalet.algafood.api.openapi.model;

import java.math.BigDecimal;

import com.marcusscalet.algafood.api.model.CuisineModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestaurantEssentialModel")
@Getter
@Setter
public class RestaurantBasicModelOpenApi {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	private String nome;
	
	@ApiModelProperty(example = "12.00")
	private BigDecimal shippingFee;
	
	@ApiModelProperty(example = "1")
	private CuisineModel cuisine;
}
