package com.marcusscalet.algafood.api.openapi.controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.marcusscalet.algafood.domain.filter.DailySalesFilter;
import com.marcusscalet.algafood.domain.model.dto.DailySales;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Statistics")
public interface StatisticsControllerOpenApi {

    @ApiOperation("Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "restaurantId", value = "ID do restaurante", 
                example = "1", dataType = "int"),
        @ApiImplicitParam(name = "creationStartDate", value = "Data/hora inicial da criação do pedido",
            example = "2019-12-01T00:00:00Z", dataTypeClass = OffsetDateTime.class),
        @ApiImplicitParam(name = "creationEndDate", value = "Data/hora final da criação do pedido",
            example = "2019-12-02T23:59:59Z", dataTypeClass = OffsetDateTime.class)})
	List<DailySales> queryDailySales(DailySalesFilter filter,
			@ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC",
	                defaultValue = "+00:00") String timeOffset);

    ResponseEntity<byte[]> queryDailySalesPdf(DailySalesFilter filter, String timeOffset);
 
 }
