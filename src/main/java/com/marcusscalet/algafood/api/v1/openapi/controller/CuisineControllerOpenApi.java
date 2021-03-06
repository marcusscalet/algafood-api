package com.marcusscalet.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.v1.model.CuisineModel;
import com.marcusscalet.algafood.api.v1.model.input.CuisineInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cuisines")
public interface CuisineControllerOpenApi {

	@ApiOperation("Lista as cozinhas com paginação")
	PagedModel<CuisineModel> listAll(Pageable pageable);

	@ApiOperation("Busca cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID de cozinha inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	CuisineModel find(@ApiParam(value = "ID da cozinha", example = "1", required = true) Long cuisineId);

	@ApiOperation("Cria uma nova cozinha")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cozinha cadastrada")})
	CuisineModel add(@ApiParam(name = "body", value = "Representação de uma cozinha", required = true) CuisineInput cuisineInput);

	@ApiOperation("Atualiza cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cozinha atualizada"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	CuisineModel update(@ApiParam(value = "ID da cozinha", example = "1", required = true) Long cuisineId,
			@ApiParam(name = "body", value = "Representação de uma cozinha com os novos dados", required = true) CuisineInput cuisineInput);

	@ApiOperation("Remove cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Cozinha removida"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	void remove(@ApiParam(value = "ID da cozinha", example = "1", required = true) Long cuisineId);
}
