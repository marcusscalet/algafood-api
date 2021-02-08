package com.marcusscalet.algafood.api.openapi.controller;

import java.util.List;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.CityDTO;
import com.marcusscalet.algafood.api.model.input.CityInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "City")
public interface CityControllerOpenApi {

	@ApiOperation("Lista todas as cidades")
	public List<CityDTO> listAll();

	@ApiOperation("Busca cidade por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID de cidade inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	public CityDTO find(@ApiParam(value = "ID da cidade", example = "1", required = true) Long cityId);

	@ApiOperation("Cria uma nova cidade")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cidade cadastrada") })
	public CityDTO add(@ApiParam(name = "body", value = "Representação de uma cidade", required = true) CityInput cityInput);

	@ApiOperation("Atualiza cidade por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	public CityDTO update(@ApiParam(value = "ID da cidade", example = "1", required = true) Long cityId,
			@ApiParam(name = "body", value = "Representação de uma cidade com os novos dados", required = true) CityInput cityInput);

	@ApiOperation("Remove cidade por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Cidade removida"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	public void remove(@ApiParam(value = "ID da cidade", example = "1", required = true) Long cityId);
}
