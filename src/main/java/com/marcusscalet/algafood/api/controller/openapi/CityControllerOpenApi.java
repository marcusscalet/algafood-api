package com.marcusscalet.algafood.api.controller.openapi;

import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;

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

	@ApiOperation("List all cities")
	public List<CityDTO> listAll();

	@ApiOperation("Find city by ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "City ID invalid", response = Problem.class),
			@ApiResponse(code = 404, message = "City not found", response = Problem.class) })
	public CityDTO find(@ApiParam("ID of a city") Long cityId);

	@ApiOperation("Register a new city")
	@ApiResponses({ @ApiResponse(code = 201, message = "City created"),
			@ApiResponse(code = 404, message = "City not found", response = Problem.class) })
	public CityDTO add(CityInput cityInput);

	@ApiOperation("Update city By Id")
	@ApiResponses({ @ApiResponse(code = 200, message = "City updated"),
			@ApiResponse(code = 404, message = "City not found", response = Problem.class) })
	@PutMapping("/{cityId}")
	public CityDTO update(@ApiParam("ID of a city") Long cityId,
			@ApiParam(name = "body", value = "Representação de uma cidade com os novos dados") CityInput cityInput);

	@ApiOperation("Delete city by ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "City removed"),
			@ApiResponse(code = 404, message = "City not found", response = Problem.class) })
	public void remove(@ApiParam("ID of a city") Long cityId);
}
