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

import com.marcusscalet.algafood.api.assembler.GroupModelAssembler;
import com.marcusscalet.algafood.api.assembler.GroupInputDisassembler;
import com.marcusscalet.algafood.api.model.GroupModel;
import com.marcusscalet.algafood.api.model.input.GroupInput;
import com.marcusscalet.algafood.api.openapi.controller.GroupControllerOpenApi;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.GroupNotFoundException;
import com.marcusscalet.algafood.domain.model.Group;
import com.marcusscalet.algafood.domain.service.GroupRegistrationService;

@RestController
@RequestMapping(path = "/groups")
public class GroupController implements GroupControllerOpenApi{

	@Autowired
	private GroupRegistrationService groupRegistrationService;

	@Autowired
	private GroupInputDisassembler groupInputDisassembler;

	@Autowired
	private GroupModelAssembler groupModelAssembler;

	@GetMapping
	public List<GroupModel> listAll() {
		List<Group> groupsList = groupRegistrationService.listAll();
		
		return groupModelAssembler.toCollectionModel(groupsList);
	}

	@GetMapping("/{groupId}")
	public GroupModel find(@PathVariable Long groupId) {
		Group group = groupRegistrationService.searchOrFail(groupId);

		return groupModelAssembler.toModel(group);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GroupModel add(@Valid @RequestBody GroupInput groupInput) {

		Group group = groupInputDisassembler.toDomainObject(groupInput);

		group = groupRegistrationService.saveGroup(group);

		return groupModelAssembler.toModel(group);
	}
	
	@PutMapping("/{groupId}")
	public GroupModel update(@PathVariable Long groupId, @Valid @RequestBody GroupInput groupInput) {
		try {
			Group currentGroup = groupRegistrationService.searchOrFail(groupId);
			
			groupInputDisassembler.copyToDomainObject(groupInput, currentGroup);
			
			return groupModelAssembler.toModel(groupRegistrationService.saveGroup(currentGroup));
		} catch(GroupNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long groupId) {
		groupRegistrationService.remove(groupId);
	}

}
