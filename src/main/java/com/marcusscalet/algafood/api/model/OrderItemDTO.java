package com.marcusscalet.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

	private Long productId;
	
	private String productName;

	private Integer amount;

	private BigDecimal unitCost;

	private BigDecimal totalCost;

	private String note;

}
