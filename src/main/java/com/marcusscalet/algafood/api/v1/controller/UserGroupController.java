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

import com.marcusscalet.algafood.api.openapi.controller.UserGroupControllerOpenApi;
import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.api.v1.assembler.GroupModelAssembler;
import com.marcusscalet.algafood.api.v1.model.GroupModel;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.service.UserRegistrationService;

@RestController
@RequestMapping(value = "/users/{userId}/groups")
public class UserGroupController implements UserGroupControllerOpenApi {

	@Autowired
	private GroupModelAssembler groupModelAssembler;
	
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public CollectionModel<GroupModel> listAll(@PathVariable Long userId){
		User user = userRegistrationService.searchOrFail(userId);
	    
	    CollectionModel<GroupModel> gruposModel = groupModelAssembler.toCollectionModel(user.getGroups())
	            .removeLinks()
	            .add(algaLinks.linkToUserGroupAssociate(userId, "associate"));
	    
	    gruposModel.getContent().forEach(grupoModel -> {
	        grupoModel.add(algaLinks.linkToUserGroupDisassociate(
	                userId, grupoModel.getId(), "disassocite"));
	    });
	    
	    return gruposModel;
	}
	
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> disassociate(@PathVariable Long userId, @PathVariable Long groupId) {
		userRegistrationService.disassociateGroup(userId, groupId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long groupId) {
		userRegistrationService.associateGroup(userId, groupId);
		
		return ResponseEntity.noContent().build();
	}
}
