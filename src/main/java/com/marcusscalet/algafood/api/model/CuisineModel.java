package com.marcusscalet.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonView;
import com.marcusscalet.algafood.api.model.view.RestaurantView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cuisines")
@Setter
@Getter
public class CuisineModel extends RepresentationModel<CuisineModel>{

	@ApiModelProperty(example = "1")
	@JsonView(RestaurantView.Summary.class)
	private Long id;
	
	@ApiModelProperty(example = "Tailandesa")
	@JsonView(RestaurantView.Summary.class)
	private String name;
}
