package com.marcusscalet.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "groups")
@Getter
@Setter
public class GroupModel extends RepresentationModel<GroupModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Gerente")
	private String name;
}
