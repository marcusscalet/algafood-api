package com.marcusscalet.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodDTO {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Cartão de crédito", required = true)
	private String description;

}
