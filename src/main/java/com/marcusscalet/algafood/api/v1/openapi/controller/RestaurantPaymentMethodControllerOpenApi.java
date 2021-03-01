package com.marcusscalet.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.v1.model.PaymentMethodModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurants")
public interface RestaurantPaymentMethodControllerOpenApi {

	@ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	CollectionModel<PaymentMethodModel> listAll(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId);

	@ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", 
            response = Problem.class)})
	ResponseEntity<Void> attach(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long paymentMethodId);

	@ApiOperation("Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", 
            response = Problem.class)})
	ResponseEntity<Void> detach(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
			@ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long paymentMethodId);

}
