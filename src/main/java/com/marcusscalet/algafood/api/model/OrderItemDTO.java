package com.marcusscalet.algafood.api.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

	@ApiModelProperty(example = "1")
	private Long productId;

	@ApiModelProperty(example = "Porco com molho agridoce")
	private String productName;

	@ApiModelProperty(example = "2")
	private Integer amount;

	@ApiModelProperty(example = "78.90")
	private BigDecimal unitCost;

	@ApiModelProperty(example = "157.80")
	private BigDecimal totalCost;

	@ApiModelProperty(example = "Menos picante, por favor")
	private String note;

}