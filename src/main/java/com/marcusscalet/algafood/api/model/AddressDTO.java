package com.marcusscalet.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

	@ApiModelProperty(example = "38400-000")
	private String zipCode;

	@ApiModelProperty(example = "Rua Floriano Peixoto")
	private String street;

	@ApiModelProperty(example = "\"1500\"")
	private String number;

	@ApiModelProperty(example = "Centro")
	private String neighborhood;

	private CityResumeDTO city;

}