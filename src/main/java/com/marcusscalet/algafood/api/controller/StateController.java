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

import com.marcusscalet.algafood.api.assembler.StateDTOAssembler;
import com.marcusscalet.algafood.api.assembler.StateInputDisassembler;
import com.marcusscalet.algafood.api.model.StateDTO;
import com.marcusscalet.algafood.api.model.input.StateInput;
import com.marcusscalet.algafood.api.openapi.controller.StateControllerOpenApi;
import com.marcusscalet.algafood.domain.model.State;
import com.marcusscalet.algafood.domain.service.StateRegistrationService;

@RestController
@RequestMapping("/states")
public class StateController implements StateControllerOpenApi{

	@Autowired
	private StateRegistrationService stateRegistrationService;

	@Autowired
	private StateDTOAssembler stateModelAssembler;
	
	@Autowired
	private StateInputDisassembler stateInputDisassembler;
	
	@GetMapping
	public List<StateDTO> listAll() {
		List<State> stateList = stateRegistrationService.listAll();
		
		return stateModelAssembler.toCollectionDTO(stateList); 
	}

	@GetMapping("/{stateId}")
	public StateDTO find(@PathVariable Long stateId) {
		State state = stateRegistrationService.searchOrFail(stateId);
		
		return stateModelAssembler.toDTO(state);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StateDTO add(@Valid @RequestBody StateInput stateInput) {
		State state = stateInputDisassembler.toDomainObject(stateInput);
		state = stateRegistrationService.saveState(state);
		
		return stateModelAssembler.toDTO(state);
	}

	@PutMapping("/{stateId}")
	public StateDTO update(@PathVariable Long stateId, @Valid @RequestBody StateInput stateInput) {

		State currentState = stateRegistrationService.searchOrFail(stateId);

		stateInputDisassembler.copyToDomainObject(stateInput, currentState);

		currentState = stateRegistrationService.saveState(currentState);
		
		return stateModelAssembler.toDTO(currentState);

	}

	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long stateId) {
		stateRegistrationService.removeState(stateId);

	}

}