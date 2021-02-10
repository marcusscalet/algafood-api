package com.marcusscalet.algafood.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.OrderDTO;
import com.marcusscalet.algafood.api.model.OrderSummaryDTO;
import com.marcusscalet.algafood.api.model.input.OrderInput;
import com.marcusscalet.algafood.domain.filter.OrderFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Orders")
public interface OrderControllerOpenApi {

	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
					name = "fields", paramType = "query", dataTypeClass = String.class) })
	@ApiOperation("Pesquisa os pedidos")
	 Page<OrderSummaryDTO> search(OrderFilter filter, Pageable pageable);

	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
					name = "fields", paramType = "query", dataTypeClass = String.class) })
	@ApiOperation("Busca um pedido por código")
	@ApiResponses({ @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class) })
	 OrderDTO find(
			@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String orderCode);

	@ApiResponses({ @ApiResponse(code = 201, message = "Pedido cadastrado") })
	@ApiOperation("Cria um novo pedido")
	 OrderDTO save(@ApiParam(name = "body", value = "Representação de um pedido", required = true) OrderInput orderInput);

} 
