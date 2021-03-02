package com.marcusscalet.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantBasicModel  extends RepresentationModel<RestaurantBasicModel> {

	   @ApiModelProperty(example = "1")
	    private Long id;
	    
	    @ApiModelProperty(example = "Thai Gourmet")
	    private String name;
	    
	    @ApiModelProperty(example = "12.00")
	    private BigDecimal shippingFee;
	    
	    private CuisineModel cuisine;
	}