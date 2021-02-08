package com.marcusscalet.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.PaymentMethodDTO;
import com.marcusscalet.algafood.api.model.input.PaymentMethodInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Payment Method")
public interface PaymentMethodControllerOpenApi {

	@ApiOperation("Lista todos os métodos de pagamento")
	public ResponseEntity<List<PaymentMethodDTO>> listAll(ServletWebRequest request);

	@ApiOperation("Busca um método de pagamento por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID de método de pagamento inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Método de pagamento não encontrado", response = Problem.class) })
	public ResponseEntity<PaymentMethodDTO> find(
			@ApiParam(value = "ID de método de pagamento", example = "1") Long paymentMethodId, ServletWebRequest request);

	@ApiResponses({ @ApiResponse(code = 201, message = "Método de pagamento cadastrado"),
		@ApiResponse(code = 404, message = "Método de pagamento não encontrado", response = Problem.class) })
		@ApiOperation("Cria um método de pagamento método de pagamento")
	public PaymentMethodDTO add(
			@ApiParam(name = "body", value = "Representação de um método de pagamento") PaymentMethodInput paymentMethodInput);

	@ApiOperation("Atualiza método de pagamento por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Método de pagamento atualizado"),
			@ApiResponse(code = 404, message = "Método de pagamento não encontrado", response = Problem.class) })
	public PaymentMethodDTO update(@ApiParam(value = "ID de método de pagamento", example = "1") Long paymentMethodId,
			@ApiParam(name = "body", value = "Representação de um novo método de pagamento") PaymentMethodInput paymentMethodInput);

	@ApiOperation("Remove método de pagamento por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Método de pagamento removido"),
			@ApiResponse(code = 404, message = "Método de pagamento não encontrado", response = Problem.class) })
	public void remover(@ApiParam(value = "ID de método de pagamento", example = "1") Long paymentMethodId);
}