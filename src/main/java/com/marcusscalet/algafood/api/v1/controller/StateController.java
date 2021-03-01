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

import com.marcusscalet.algafood.api.v1.assembler.StateInputDisassembler;
import com.marcusscalet.algafood.api.v1.assembler.StateModelAssembler;
import com.marcusscalet.algafood.api.v1.model.StateModel;
import com.marcusscalet.algafood.api.v1.model.input.StateInput;
import com.marcusscalet.algafood.api.v1.openapi.controller.StateControllerOpenApi;
import com.marcusscalet.algafood.domain.model.State;
import com.marcusscalet.algafood.domain.service.StateRegistrationService;

@RestController
@RequestMapping(path = "/v1/states")
public class StateController implements StateControllerOpenApi{

	@Autowired
	private StateRegistrationService stateRegistrationService;

	@Autowired
	private StateModelAssembler stateModelAssembler;
	
	@Autowired
	private StateInputDisassembler stateInputDisassembler;
	
	@GetMapping
	public CollectionModel<StateModel> listAll() {
		List<State> stateList = stateRegistrationService.listAll();
		
		return stateModelAssembler.toCollectionModel(stateList); 
	}

	@GetMapping("/{stateId}")
	public StateModel find(@PathVariable Long stateId) {
		State state = stateRegistrationService.searchOrFail(stateId);
		
		return stateModelAssembler.toModel(state);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StateModel add(@Valid @RequestBody StateInput stateInput) {
		State state = stateInputDisassembler.toDomainObject(stateInput);
		state = stateRegistrationService.saveState(state);
		
		return stateModelAssembler.toModel(state);
	}

	@PutMapping("/{stateId}")
	public StateModel update(@PathVariable Long stateId, @Valid @RequestBody StateInput stateInput) {

		State currentState = stateRegistrationService.searchOrFail(stateId);

		stateInputDisassembler.copyToDomainObject(stateInput, currentState);

		currentState = stateRegistrationService.saveState(currentState);
		
		return stateModelAssembler.toModel(currentState);

	}

	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long stateId) {
		stateRegistrationService.removeState(stateId);

	}

}