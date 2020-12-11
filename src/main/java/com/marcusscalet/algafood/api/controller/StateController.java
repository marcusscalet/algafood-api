package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.marcusscalet.algafood.domain.model.State;
import com.marcusscalet.algafood.domain.repository.StateRepository;
import com.marcusscalet.algafood.domain.service.StateRegistrationService;

@RestController
@RequestMapping("/states")
public class StateController {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private StateRegistrationService stateRegistrationService;

	@GetMapping
	public List<State> listAll() {
		return stateRepository.findAll();
	}

	@GetMapping("/{stateId}")
	public State find(@PathVariable Long stateId) {
		return stateRegistrationService.searchOrFail(stateId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public State add(@Valid @RequestBody State state) {
		return stateRegistrationService.saveState(state);
	}

	@PutMapping("/{stateId}")
	public State update(@PathVariable Long stateId, @Valid @RequestBody State state) {

		State currentState = stateRegistrationService.searchOrFail(stateId);

		BeanUtils.copyProperties(state, currentState, "id");

		return stateRegistrationService.saveState(currentState);

	}

	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long stateId) {
		stateRegistrationService.removeState(stateId);

	}

}