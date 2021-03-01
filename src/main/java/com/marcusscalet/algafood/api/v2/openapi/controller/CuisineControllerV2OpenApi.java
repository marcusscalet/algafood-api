package com.marcusscalet.algafood.api.v2.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.v2.model.CuisineModelV2;
import com.marcusscalet.algafood.api.v2.model.input.CuisineInputV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cuisines")
public interface CuisineControllerV2OpenApi {

	@ApiOperation("Lista as cozinhas com paginação")
	PagedModel<CuisineModelV2> listAll(Pageable pageable);

	@ApiOperation("Busca cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID de cozinha inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	CuisineModelV2 find(@ApiParam(value = "ID da cozinha", example = "1", required = true) Long cuisineId);

	@ApiOperation("Cria uma nova cozinha")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cozinha cadastrada")})
	CuisineModelV2 add(@ApiParam(name = "body", value = "Representação de uma cozinha", required = true) CuisineInputV2 cuisineInput);

	@ApiOperation("Atualiza cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cozinha atualizada"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	CuisineModelV2 update(@ApiParam(value = "ID da cozinha", example = "1", required = true) Long cuisineId,
			@ApiParam(name = "body", value = "Representação de uma cozinha com os novos dados", required = true) CuisineInputV2 cuisineInput);

	@ApiOperation("Remove cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Cozinha removida"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	void remove(@ApiParam(value = "ID da cozinha", example = "1", required = true) Long cuisineId);
}
