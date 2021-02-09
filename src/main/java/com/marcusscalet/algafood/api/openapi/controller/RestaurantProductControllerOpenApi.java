package com.marcusscalet.algafood.api.openapi.controller;

import java.util.List;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.ProductDTO;
import com.marcusscalet.algafood.api.model.input.ProductInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Product")
public interface RestaurantProductControllerOpenApi {

	@ApiOperation("Lista os produtos de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	List<ProductDTO> listAll(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem", 
            example = "false", defaultValue = "false") boolean includeInactive);

	@ApiOperation("Busca um produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)})
	ProductDTO findById( @ApiParam(value = "ID do produto", example = "1", required = true) Long productId,  
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Cadastra um produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Produto cadastrado"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	ProductDTO add(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId, 
			@ApiParam(name = "body", value = "Representação de um novo produto", required = true) ProductInput productInput);

	@ApiOperation("Atualiza um produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Produto atualizado"),
        @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)})
	ProductDTO update(
			@ApiParam(value = "ID do produto", example = "1", required = true) Long productId, 
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
			@ApiParam(name = "body", value = "Representação de um produto com os novos dados", 
            required = true) ProductInput productInput);
}
