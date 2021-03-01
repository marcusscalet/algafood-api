package com.marcusscalet.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodInput {

	@ApiModelProperty(example = "Cartão de crédito", required = true)
	@NotBlank
	private String description;
}
