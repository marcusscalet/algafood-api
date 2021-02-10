package com.marcusscalet.algafood.api.openapi.controller;

import java.util.List;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.GroupDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Users")
public interface UserGroupControllerOpenApi {

	@ApiOperation("Lista os grupos associados a um usuário")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)})
	List<GroupDTO> listAll(
			@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId);
	
	@ApiOperation("Desassociação de grupo com usuário")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", 
            response = Problem.class)})
	void disassociate(
			@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId, 
			@ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId);
	
	@ApiOperation("Associação de grupo com usuário")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", 
            response = Problem.class)})
	void associate(
			@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId, 
			@ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId);
}
