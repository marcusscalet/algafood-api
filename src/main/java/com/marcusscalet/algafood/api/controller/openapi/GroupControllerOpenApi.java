package com.marcusscalet.algafood.api.controller.openapi;

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
	public GroupDTO find(@ApiParam("ID do grupo") Long groupId);

	@ApiResponses({ @ApiResponse(code = 201, message = "Grupo cadastrado"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	@ApiOperation("Cria um novo grupo")
	public GroupDTO add(GroupInput groupInput);

	@ApiOperation("Atualiza grupo por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Grupo atualizado"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	public GroupDTO update(@ApiParam("ID do grupo") Long groupId, GroupInput groupInput);

	@ApiOperation("Remove grupo por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Grupo removido"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	public void remove(@ApiParam("ID do grupo") Long groupId);

}
