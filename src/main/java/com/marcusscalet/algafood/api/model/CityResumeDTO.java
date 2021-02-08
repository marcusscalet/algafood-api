package com.marcusscalet.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityResumeDTO {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Uberlândia")
	private String name;

	@ApiModelProperty(example = "Minas Gerais")
	private String state;

}
