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
	public List<GroupDTO> listAll();

	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID de grupo inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	public GroupDTO find(@ApiParam(value = "ID do grupo", example = "1") Long groupId);

	@ApiResponses({ @ApiResponse(code = 201, message = "Grupo cadastrado"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	@ApiOperation("Cria um novo grupo")
	public GroupDTO add(@ApiParam(name = "body", value = "Representação de um grupo") GroupInput groupInput);

	@ApiOperation("Atualiza grupo por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Grupo atualizado"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	public GroupDTO update(@ApiParam(value = "ID do grupo", example = "1") Long groupId,
			@ApiParam(name = "body", value = "Representação de um grupo com os novos dados") GroupInput groupInput);

	@ApiOperation("Remove grupo por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Grupo removido"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	public void remove(@ApiParam(value = "ID do grupo", example = "1") Long groupId);

}