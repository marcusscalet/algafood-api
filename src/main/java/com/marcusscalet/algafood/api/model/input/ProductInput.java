package com.marcusscalet.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInput {

	@ApiModelProperty(example = "Espetinho de Cupim", required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete", required = true)
	@NotBlank
	private String description;

	@ApiModelProperty(example = "12.50", required = true)
	@NotNull
	@PositiveOrZero
	private BigDecimal price;

	@ApiModelProperty(example = "true", required = true)
	@NotNull
	private boolean active;
}
