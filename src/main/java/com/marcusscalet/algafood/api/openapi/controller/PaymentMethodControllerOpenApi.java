package com.marcusscalet.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.PaymentMethodModel;
import com.marcusscalet.algafood.api.model.input.PaymentMethodInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Payment Methods")
public interface PaymentMethodControllerOpenApi {

	@ApiOperation("Lista todos os métodos de pagamento")
	 ResponseEntity<CollectionModel<PaymentMethodModel>> listAll(ServletWebRequest request);

	@ApiOperation("Busca um método de pagamento por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID de método de pagamento inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Método de pagamento não encontrado", response = Problem.class) })
	 ResponseEntity<PaymentMethodModel> find(
			@ApiParam(value = "ID de método de pagamento", example = "1", required = true) Long paymentMethodId, ServletWebRequest request);

	@ApiResponses({ @ApiResponse(code = 201, message = "Método de pagamento cadastrado")})
		@ApiOperation("Cria um novo método de pagamento")
	 PaymentMethodModel add(
			@ApiParam(name = "body", value = "Representação de um método de pagamento", required = true) PaymentMethodInput paymentMethodInput);

	@ApiOperation("Atualiza método de pagamento por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Método de pagamento atualizado"),
			@ApiResponse(code = 404, message = "Método de pagamento não encontrado", response = Problem.class) })
	 PaymentMethodModel update(@ApiParam(value = "ID de método de pagamento", example = "1", required = true) Long paymentMethodId,
			@ApiParam(name = "body", value = "Representação de um novo método de pagamento", required = true) PaymentMethodInput paymentMethodInput);

	@ApiOperation("Remove método de pagamento por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Método de pagamento removido"),
			@ApiResponse(code = 404, message = "Método de pagamento não encontrado", response = Problem.class) })
	 void remover(@ApiParam(value = "ID de método de pagamento", example = "1", required = true) Long paymentMethodId);
}
