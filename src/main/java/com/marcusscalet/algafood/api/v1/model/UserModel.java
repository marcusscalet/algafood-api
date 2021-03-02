package com.marcusscalet.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "users")
@Getter
@Setter
public class UserModel extends RepresentationModel<UserModel>{

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "John Doe")
	private String name;

	@ApiModelProperty(example = "johndoe@algafood.com.br")
	private String email;
}
