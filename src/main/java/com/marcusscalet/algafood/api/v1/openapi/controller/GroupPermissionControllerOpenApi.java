package com.marcusscalet.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.v1.model.PermissionModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Groups")
public interface GroupPermissionControllerOpenApi {

	@ApiOperation("Lista as permissões associadas a um grupo")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)})
	CollectionModel<PermissionModel> listAll(@ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId);

	@ApiOperation("Associação de permissão com grupo")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada", 
            response = Problem.class)})
	ResponseEntity<Void> attach(@ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId,
			@ApiParam(value = "ID da permissão", example = "1", required = true) Long permissionId);

	 @ApiOperation("Desassociação de permissão com grupo")
	    @ApiResponses({
	        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
	        @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada", 
	            response = Problem.class)})
	 ResponseEntity<Void> detach(@ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId, 
			@ApiParam(value = "ID da permissão", example = "1", required = true) Long permissionId);
}
