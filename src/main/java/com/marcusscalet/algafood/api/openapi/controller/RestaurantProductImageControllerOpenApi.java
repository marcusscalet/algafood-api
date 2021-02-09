package com.marcusscalet.algafood.api.openapi.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.ProductImageDTO;
import com.marcusscalet.algafood.api.model.input.ProductImageInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Product")
public interface RestaurantProductImageControllerOpenApi {

	@ApiOperation("Atualiza a foto do produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Foto do produto atualizada"),
        @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
	public ProductImageDTO updateImage(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId, 
			@ApiParam(value = "ID do produto", example = "1", required = true) Long productId,
			@Valid ProductImageInput productImageInput)throws IOException;

	@ApiOperation(value = "Busca a foto do produto de um restaurante",
            produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
	public ProductImageDTO searchImage(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId, 
			@ApiParam(value = "ID do produto", example = "1", required = true) Long productId);

    @ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
	public ResponseEntity<?> showImage(@PathVariable Long restaurantId, @PathVariable Long productId,
			@RequestHeader(name = "accept") String acceptHeader)throws HttpMediaTypeNotAcceptableException;

    
    @ApiOperation("Exclui a foto do produto de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Foto do produto excluída"),
        @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
	public void remove(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "ID do produto", example = "1", required = true)Long productId);
}
