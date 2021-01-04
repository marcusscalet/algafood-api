package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.marcusscalet.algafood.api.assembler.UserDTOAssembler;
import com.marcusscalet.algafood.api.assembler.UserInputDisassembler;
import com.marcusscalet.algafood.api.model.UserDTO;
import com.marcusscalet.algafood.api.model.input.PasswordInput;
import com.marcusscalet.algafood.api.model.input.UserInput;
import com.marcusscalet.algafood.api.model.input.UserWithPasswordInput;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.UserNotFoundException;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.service.UserRegistrationService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserDTOAssembler userDTOAssembler;

	@Autowired
	private UserInputDisassembler userInputDisassembler;

	@Autowired
	private UserRegistrationService userRegistrationService;

	@GetMapping
	public List<UserDTO> list() {
		return userDTOAssembler.toCollectionDTO(userRegistrationService.listAll());
	}

	@GetMapping("/{userId}")
	public UserDTO find(@PathVariable Long userId) {

		return userDTOAssembler.toDTO(userRegistrationService.searchOrFail(userId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO add(@Valid @RequestBody UserWithPasswordInput userWithPasswordInput) {

		User user = userInputDisassembler.toDomainObject(userWithPasswordInput);

		user = userRegistrationService.save(user);

		return userDTOAssembler.toDTO(user);
	}

	@PutMapping("/{userId}")
	public UserDTO update(@PathVariable Long userId, @Valid @RequestBody UserInput userInput) {
		try {
			User currentUser = userRegistrationService.searchOrFail(userId);

			userInputDisassembler.copyToDomainObject(userInput, currentUser);

			return userDTOAssembler.toDTO(userRegistrationService.save(currentUser));
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
