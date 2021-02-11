package com.marcusscalet.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.UserModel;
import com.marcusscalet.algafood.api.model.input.PasswordInput;
import com.marcusscalet.algafood.api.model.input.UserInput;
import com.marcusscalet.algafood.api.model.input.UserWithPasswordInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Users")
public interface UserControllerOpenApi {
    
	@ApiOperation("Lista os usuários")
	public List<UserModel> list();
	
	@ApiOperation("Busca um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
	UserModel find(@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId);

	@ApiOperation("Cadastra um usuário")
	@ApiResponses({ @ApiResponse(code = 201, message = "Usuário cadastrado"), })
	UserModel add(
			@ApiParam(name = "body", value = "Representação de um novo usuário", required = true) UserWithPasswordInput userWithPasswordInput);

	@ApiOperation("Atualiza um usuário por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Usuário atualizado"),
			@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class) })
	UserModel update(@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId,
			@ApiParam(name = "body", value = "Representação de um usuário com os novos dados", required = true) UserInput userInput);

	void remove(@PathVariable Long userId);

	@ApiOperation("Atualiza a senha de um usuário")
	@ApiResponses({ @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
			@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class) })
	void updatePassword(@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId,
			@ApiParam(name = "body", value = "Representação de uma nova senha", required = true) PasswordInput password);
}
