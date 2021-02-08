package com.marcusscalet.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFilter {
	
	@ApiModelProperty(example = "1", value = "ID do cliente para filtro da pesquisa")
	private Long clientId;
	
	@ApiModelProperty(example = "1", value = "ID do restaurante para filtro da pesquisa")
	private Long restaurantId;

	@ApiModelProperty(example = "2019-10-30T00:00:00Z", value = "Data/hora de criação inicial para filtro da pesquisa")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime creationStartDate;

	@ApiModelProperty(example = "2019-11-01T10:00:00Z", value = "Data/hora de criação final para filtro da pesquisa")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime creationEndDate;
}
