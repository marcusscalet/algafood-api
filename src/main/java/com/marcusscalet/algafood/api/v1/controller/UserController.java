package com.marcusscalet.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.v1.assembler.UserInputDisassembler;
import com.marcusscalet.algafood.api.v1.assembler.UserModelAssembler;
import com.marcusscalet.algafood.api.v1.model.UserModel;
import com.marcusscalet.algafood.api.v1.model.input.PasswordInput;
import com.marcusscalet.algafood.api.v1.model.input.UserInput;
import com.marcusscalet.algafood.api.v1.model.input.UserWithPasswordInput;
import com.marcusscalet.algafood.api.v1.openapi.controller.UserControllerOpenApi;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.UserNotFoundException;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.service.UserRegistrationService;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController implements UserControllerOpenApi {

	@Autowired
	private UserModelAssembler userModelAssembler;

	@Autowired
	private UserInputDisassembler userInputDisassembler;

	@Autowired
	private UserRegistrationService userRegistrationService;

	@GetMapping
	public CollectionModel<UserModel> list() {
		List<User> usersList = userRegistrationService.listAll();
		
		return userModelAssembler.toCollectionModel(usersList);
	}

	@GetMapping("/{userId}")
	public UserModel find(@PathVariable Long userId) {

		return userModelAssembler.toModel(userRegistrationService.searchOrFail(userId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserModel add(@Valid @RequestBody UserWithPasswordInput userWithPasswordInput) {

		User user = userInputDisassembler.toDomainObject(userWithPasswordInput);

		user = userRegistrationService.save(user);

		return userModelAssembler.toModel(user);
	}

	@PutMapping("/{userId}")
	public UserModel update(@PathVariable Long userId, @Valid @RequestBody UserInput userInput) {
		try {
			User currentUser = userRegistrationService.searchOrFail(userId);

			userInputDisassembler.copyToDomainObject(userInput, currentUser);

			return userModelAssembler.toModel(userRegistrationService.save(currentUser));
		} catch (UserNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long userId) {
		userRegistrationService.remove(userId);
	}

	@PutMapping("/{userId}/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePassword(@PathVariable Long userId, @RequestBody @Valid PasswordInput password) {

		userRegistrationService.updatePassword(userId, password.getCurrentPassword(), password.getNewPassword());

	}
}