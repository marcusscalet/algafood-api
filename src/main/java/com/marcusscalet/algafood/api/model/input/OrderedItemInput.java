package com.marcusscalet.algafood.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderedItemInput {

	@NotNull
	private Long productId;
	
	@NotNull
	@PositiveOrZero
	private Integer amount;
	
	private String note;
}
