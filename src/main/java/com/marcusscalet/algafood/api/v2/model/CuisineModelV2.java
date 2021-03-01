package com.marcusscalet.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CuisineModel")
@Relation(collectionRelation = "cuisines")
@Setter
@Getter
public class CuisineModelV2 extends RepresentationModel<CuisineModelV2>{

	@ApiModelProperty(example = "1")
	private Long cuisineId;
	
	@ApiModelProperty(example = "Tailandesa")
	private String cuisineName;
}
