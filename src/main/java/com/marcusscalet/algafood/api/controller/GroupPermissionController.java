package com.marcusscalet.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.assembler.PermissionDTOAssembler;
import com.marcusscalet.algafood.api.model.PermissionDTO;
import com.marcusscalet.algafood.domain.model.Group;
import com.marcusscalet.algafood.domain.service.GroupRegistrationService;

@RestController
@RequestMapping(value = "/groups/{groupId}/permissions")
public class GroupPermissionController {

	@Autowired
	private GroupRegistrationService groupRegistrationService;

	@Autowired
	private PermissionDTOAssembler permissionDTOAssembler;

	@GetMapping
	public List<PermissionDTO> listAll(@PathVariable Long groupId) {
		Group group = groupRegistrationService.searchOrFail(groupId);

		return permissionDTOAssembler.toDTOCollection(group.getPermissions());
	}

	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void attach(@PathVariable Long groupId, @PathVariable Long permissionId) {

		groupRegistrationService.associatePermission(groupId, permissionId);
	}

	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void detach(@PathVariable Long groupId, @PathVariable Long permissionId) {
 
		groupRegistrationService.disassociatePermission(groupId, permissionId);
	}
}
