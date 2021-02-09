package com.marcusscalet.algafood.api.openapi.controller;

import java.util.List;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.GroupDTO;
import com.marcusscalet.algafood.api.model.input.GroupInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Group")
public interface GroupControllerOpenApi {

	@ApiOperation("Lista todos os grupos")
	List<GroupDTO> listAll();

	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID de grupo inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	GroupDTO find(@ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId);

	@ApiResponses({ @ApiResponse(code = 201, message = "Grupo cadastrado")})
	@ApiOperation("Cria um novo grupo")
	GroupDTO add(@ApiParam(name = "body", value = "Representação de um grupo", required = true) GroupInput groupInput);

	@ApiOperation("Atualiza grupo por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Grupo atualizado"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	GroupDTO update(@ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId,
			@ApiParam(name = "body", value = "Representação de um grupo com os novos dados", required = true) GroupInput groupInput);

	@ApiOperation("Remove grupo por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Grupo removido"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	void remove(@ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId);

}