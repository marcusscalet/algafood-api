package com.marcusscalet.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityInput {

	@ApiModelProperty(example = "Itu", required = true)
	@NotBlank
	private String name;

	@Valid
	@NotNull
	private StateIdInput state;
}
