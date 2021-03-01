package com.marcusscalet.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressInput {

	@ApiModelProperty(example = "38400-000", required = true)
	@NotBlank
	private String zipCode;

	@ApiModelProperty(example = "Rua Floriano Peixoto", required = true)
	@NotBlank
	private String street;

	@ApiModelProperty(example = "\"1500\"", required = true)
	@NotBlank
	private String number;

	@ApiModelProperty(example = "Centro", required = true)
	@NotBlank
	private String neighborhood;

	@Valid
	@NotNull
	private CityIdInput city;
}
