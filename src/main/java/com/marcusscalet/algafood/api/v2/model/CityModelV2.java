package com.marcusscalet.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CityModel")
@Relation(collectionRelation = "cities")
@Getter
@Setter
public class CityModelV2 extends RepresentationModel<CityModelV2>{

	@ApiModelProperty(example = "1")
	private Long cityId;
	
	@ApiModelProperty(example = "Itu")
	private String cityName;
	
	private Long stateId;
	private String stateName;
}
