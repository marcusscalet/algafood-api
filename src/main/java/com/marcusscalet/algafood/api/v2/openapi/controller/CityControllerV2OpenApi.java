package com.marcusscalet.algafood.api.v2.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.v2.model.CityModelV2;
import com.marcusscalet.algafood.api.v2.model.input.CityInputV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cities")
public interface CityControllerV2OpenApi {

	@ApiOperation("Lista todas as cidades")
	CollectionModel<CityModelV2> listAll();

	@ApiOperation("Busca cidade por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID de cidade inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	CityModelV2 find(@ApiParam(value = "ID da cidade", example = "1", required = true) Long cityId);

	@ApiOperation("Cria uma nova cidade")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cidade cadastrada") })
	CityModelV2 add(@ApiParam(name = "body", value = "Representação de uma cidade", required = true) CityInputV2 cityInput);

	@ApiOperation("Atualiza cidade por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	CityModelV2 update(@ApiParam(value = "ID da cidade", example = "1", required = true) Long cityId,
			@ApiParam(name = "body", value = "Representação de uma cidade com os novos dados", required = true) CityInputV2 cityInput);

	@ApiOperation("Remove cidade por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Cidade removida"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	void remove(@ApiParam(value = "ID da cidade", example = "1", required = true) Long cityId);
}
