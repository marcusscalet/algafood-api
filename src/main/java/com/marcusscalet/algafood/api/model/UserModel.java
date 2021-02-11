package com.marcusscalet.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "John Doe")
	private String name;

	@ApiModelProperty(example = "johndoe@algafood.com.br")
	private String email;
}
