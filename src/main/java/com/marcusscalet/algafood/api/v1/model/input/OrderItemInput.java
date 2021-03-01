package com.marcusscalet.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemInput {

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long productId;

	@ApiModelProperty(example = "2", required = true)
	@NotNull
	@PositiveOrZero
	private Integer amount;

	@ApiModelProperty(example = "Menos picante, por favor")
	private String note;
}
