package com.marcusscalet.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.v1.model.UserModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurants")
public interface RestaurantUserControllerOpenApi {

	@ApiOperation("Lista os usuários responsáveis associados a restaurante")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	CollectionModel<UserModel> listAll(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Associação de restaurante com usuário responsável")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", 
            response = Problem.class)
    })
	ResponseEntity<Void> associateUser(
			@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId, 
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Desassociação de restaurante com usuário responsável")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado", 
            response = Problem.class)
    })
	ResponseEntity<Void> disassociateUser(
			@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId, 
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId);
}
