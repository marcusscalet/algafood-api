package com.marcusscalet.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cities")
@Setter
@Getter
public class CityResumeModel extends RepresentationModel<CityResumeModel>{

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Uberlândia")
	private String name;

	@ApiModelProperty(example = "Minas Gerais")
	private String state;

}
