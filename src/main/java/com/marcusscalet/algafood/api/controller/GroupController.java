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

import com.marcusscalet.algafood.api.assembler.GroupDTOAssembler;
import com.marcusscalet.algafood.api.assembler.GroupInputDisassembler;
import com.marcusscalet.algafood.api.model.GroupDTO;
import com.marcusscalet.algafood.api.model.input.GroupInput;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.GroupNotFoundException;
import com.marcusscalet.algafood.domain.model.Group;
import com.marcusscalet.algafood.domain.service.GroupRegistrationService;
import com.marcusscalet.algafood.infrastructure.repository.GroupRepository;

@RestController
@RequestMapping(value = "/groups")
public class GroupController {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private GroupRegistrationService groupRegistrationService;

	@Autowired
	private GroupInputDisassembler groupInputDisassembler;

	@Autowired
	private GroupDTOAssembler groupDTOAssembler;

	@GetMapping
	public List<GroupDTO> listAll() {
		List<Group> groupsList = groupRepository.findAll();
		
		return groupDTOAssembler.toCollectionDTO(groupsList);
	}
	

	@GetMapping("/{groupId}")
	public GroupDTO find(@PathVariable Long groupId) {
		Group group = groupRegistrationService.searchOrFail(groupId);

		return groupDTOAssembler.toDTO(group);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GroupDTO add(@Valid @RequestBody GroupInput groupInput) {

		Group group = groupInputDisassembler.toDomainObject(groupInput);

		group = groupRegistrationService.saveGroup(group);

		return groupDTOAssembler.toDTO(group);
	}
	
	@PutMapping
	public GroupDTO update(@PathVariable Long groupId, @Valid @RequestBody GroupInput groupInput) {
		try {
			Group currentGroup = groupRegistrationService.searchOrFail(groupId);
			
			groupInputDisassembler.copyToDomainObject(groupInput, currentGroup);
			
			return groupDTOAssembler.toDTO(groupRegistrationService.saveGroup(currentGroup));
		} catch(GroupNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remoe(@PathVariable Long groupId) {
		groupRegistrationService.remove(groupId);
	}

}
