package com.marcusscalet.algafood.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DailySales {

	private Date date;
	private Long totalSales;
	private BigDecimal totalBilled;
}
