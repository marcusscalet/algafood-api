package com.marcusscalet.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.domain.exception.EntityInUseException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.State;
import com.marcusscalet.algafood.domain.repository.StateRepository;
import com.marcusscalet.algafood.domain.service.StateRegistrationService;

@RestController
@RequestMapping("/estados")
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
	public ResponseEntity<State> find(@PathVariable Long stateId) {
		Optional<State> state = stateRepository.findById(stateId);

		if (state.isPresent()) {
			return ResponseEntity.ok(state.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public State add(@RequestBody State state) {
		return stateRegistrationService.saveState(state);
	}

	@PutMapping("/{stateId}")
	public ResponseEntity<State> update(@PathVariable Long stateId, @RequestBody State state) {
		State currentState = stateRepository.findById(stateId).orElse(null);

		if (currentState != null) {
			BeanUtils.copyProperties(state, currentState, "id");

			currentState = stateRegistrationService.saveState(currentState);
			return ResponseEntity.ok(currentState);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{stateId}")
	public ResponseEntity<?> remove(@PathVariable Long stateId) {
		try {
			stateRegistrationService.removeState(stateId);
			return ResponseEntity.noContent().build();

		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}