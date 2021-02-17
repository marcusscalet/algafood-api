package com.marcusscalet.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.RestaurantBasicModel;
import com.marcusscalet.algafood.api.model.RestaurantModel;
import com.marcusscalet.algafood.api.model.RestaurantOnlyNameModel;
import com.marcusscalet.algafood.api.model.input.RestaurantInput;
import com.marcusscalet.algafood.api.openapi.model.RestaurantBasicModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurants")
public interface RestaurantControllerOpenApi {

	@ApiOperation(value = "Restaurants list", response = RestaurantBasicModelOpenApi.class) //used here just because JsonView
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "only-name",
				name = "view", paramType = "query", dataTypeClass = String.class)
	})
//	@JsonView(RestaurantView.Summary.class)
	CollectionModel<RestaurantBasicModel> listAllSummary();
	
	@ApiOperation(value = "Restaurants list", hidden = true)
	CollectionModel<RestaurantOnlyNameModel> listAllByName();
	
	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
	    @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
	    @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	 RestaurantModel find(
		@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Cadastra um restaurante")
	@ApiResponses({
	    @ApiResponse(code = 201, message = "Restaurante cadastrado"),
	})
	 RestaurantModel add(
		@ApiParam(name = "body", value = "Representação de um restaurante", required = true) RestaurantInput restaurantInput);

	@ApiOperation("Atualiza restaurante por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Restaurante atualizado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	 RestaurantModel update(
		@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId,
		@ApiParam(name = "body", value = "Representação de um restaurante", required = true) RestaurantInput restaurantInput);

	@ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	ResponseEntity<Void> openRestaurant(@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId);

	@ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	ResponseEntity<Void> closeRestaurant(@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId);
	
	@ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	ResponseEntity<Void> activate(@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId);

	@ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	ResponseEntity<Void> inactivate(@ApiParam(value =  "ID do restaurante", example = "1", required = true) Long restaurantId);

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
