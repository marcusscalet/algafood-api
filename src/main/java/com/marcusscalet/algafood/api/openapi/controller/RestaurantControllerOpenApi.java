package com.marcusscalet.algafood.api.openapi.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.RestaurantDTO;
import com.marcusscalet.algafood.api.model.input.RestaurantInput;
import com.marcusscalet.algafood.api.model.view.RestaurantView;
import com.marcusscalet.algafood.api.openapi.model.RestaurantEssentialDTOOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurant")
public interface RestaurantControllerOpenApi {

	@ApiOperation(value = "Restaurants list", response = RestaurantEssentialDTOOpenApi.class) //used here just because JsonView
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "only-name",
				name = "view", paramType = "query", type = "string")
	})
	@JsonView(RestaurantView.Summary.class)
	 List<RestaurantDTO> listAllSummary();
	
	@ApiOperation(value = "Restaurants list", hidden = true)
	 List<RestaurantDTO> listAllByName();
	
	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
	    @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
	    @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	 RestaurantDTO find(
		@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Cadastra um restaurante")
	@ApiResponses({
	    @ApiResponse(code = 201, message = "Restaurante cadastrado"),
	})
	 RestaurantDTO add(
		@ApiParam(name = "body", value = "Representação de um restaurante", required = true) RestaurantInput restaurantInput);

	@ApiOperation("Atualiza restaurante por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Restaurante atualizado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	 RestaurantDTO update(
		@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId,
		@ApiParam(name = "body", value = "Representação de um restaurante", required = true) RestaurantInput restaurantInput);

	@ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	 void openRestaurant(@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId);

	@ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	 void closeRestaurant(@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	 void activate(@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId);

	@ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	 void inactivate(@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId);

	@ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
	 void multipleActivation(
			@ApiParam(name = "body", value = "IDs de restaurantes", required = true) List<Long> restaurantIds);

	@ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
	 void multipleInactivation(
			@ApiParam(name = "body", value = "IDs de restaurantes", required = true) List<Long> restaurantIds);
}
