package com.marcusscalet.algafood.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.CuisineDTO;
import com.marcusscalet.algafood.api.model.input.CuisineInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cuisine")
public interface CuisineControllerOpenApi {

	@ApiOperation("Lista as cozinhas com paginação")
	public Page<CuisineDTO> listAll(Pageable pageable);

	@ApiOperation("Busca cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID de cozinha inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	public CuisineDTO find(@ApiParam(value = "ID da cozinha", example = "1") Long cuisineId);

	@ApiOperation("Cria uma nova cozinha")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cozinha cadastrada"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	public CuisineDTO add(@ApiParam(name = "body", value = "Representação de uma cozinha") CuisineInput cuisineInput);

	@ApiOperation("Atualiza cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cozinha atualizada"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	public CuisineDTO update(@ApiParam(value = "ID da cozinha", example = "1") Long cuisineId,
			@ApiParam(name = "body", value = "Representação de uma cozinha com os novos dados") CuisineInput cuisineInput);

	@ApiOperation("Remove cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Cozinha removida"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	public void remove(@ApiParam(value = "ID da cozinha", example = "1") Long cuisineId);
}
