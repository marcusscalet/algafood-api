package com.marcusscalet.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.openapi.controller.GroupPermissionControllerOpenApi;
import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.api.v1.assembler.PermissionModelAssembler;
import com.marcusscalet.algafood.api.v1.model.PermissionModel;
import com.marcusscalet.algafood.domain.model.Group;
import com.marcusscalet.algafood.domain.service.GroupRegistrationService;

@RestController
@RequestMapping(path = "/v1/groups/{groupId}/permissions")
public class GroupPermissionController implements GroupPermissionControllerOpenApi {

	@Autowired
	private GroupRegistrationService groupRegistrationService;

	@Autowired
	private PermissionModelAssembler permissionModelAssembler;

	@Autowired
	private AlgaLinks algaLinks;

	@Override
	@GetMapping
	public CollectionModel<PermissionModel> listAll(@PathVariable Long groupId) {

		Group group = groupRegistrationService.searchOrFail(groupId);

		CollectionModel<PermissionModel> permissionsModel = permissionModelAssembler
				.toCollectionModel(group.getPermissions()).removeLinks().add(algaLinks.linkToGroupPermissions(groupId))
				.add(algaLinks.linkToGroupPermissionAttach(groupId, "attach"));

		permissionsModel.getContent().forEach(permissionModel -> {
			permissionModel.add(algaLinks.linkToGroupPermissionDetach(groupId, permissionModel.getId(), "desassociar"));
		});

		return permissionsModel;
	}

	@Override
	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> attach(@PathVariable Long groupId, @PathVariable Long permissionId) {

		groupRegistrationService.associatePermission(groupId, permissionId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> detach(@PathVariable Long groupId, @PathVariable Long permissionId) {

		groupRegistrationService.disassociatePermission(groupId, permissionId);

		return ResponseEntity.noContent().build();
	}
}
