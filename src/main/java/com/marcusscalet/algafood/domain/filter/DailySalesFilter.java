package com.marcusscalet.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailySalesFilter {

	private Long restaurantId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime creationStartDate;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime creationEndDate;
}
