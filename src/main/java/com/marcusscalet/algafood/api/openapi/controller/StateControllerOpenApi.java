package com.marcusscalet.algafood.api.openapi.controller;

import java.util.List;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.StateModel;
import com.marcusscalet.algafood.api.model.input.StateInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "States")
public interface StateControllerOpenApi {
	
	@ApiOperation("Lista todos os estados")
	 List<StateModel> listAll();
	
	@ApiOperation("Busca estado por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID de estado inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class) })
	 StateModel find(@ApiParam(value = "ID do estado", example = "1", required = true) Long stateId);
	
	@ApiOperation("Cria um novo estado")
	@ApiResponses({ @ApiResponse(code = 201, message = "Estado cadastrado") })
	 StateModel add(@ApiParam(name = "body", value = "Representação de um estado com os novos dados", required = true)  StateInput stateInput);
	
	@ApiOperation("Atualiza estado por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Estado atualizado"),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class) })
	 StateModel update(@ApiParam(value = "ID do estado", example = "1", required = true) Long stateId,
			@ApiParam(name = "body", value = "Representação de um estado com os novos dados", required = true)  StateInput stateInput);

	@ApiOperation("Remove estado por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Estado removido"),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class) })
	 void remove(@ApiParam(value = "ID do estado", example = "1", required = true) Long stateId);
}
