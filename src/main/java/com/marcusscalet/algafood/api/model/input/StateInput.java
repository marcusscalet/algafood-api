package com.marcusscalet.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import com.marcusscalet.algafood.domain.model.State;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateInput {

	@ApiModelProperty(example = "Minas Gerais", required = true)
	@NotBlank
	private State name;
}
