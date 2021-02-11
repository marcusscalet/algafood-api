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

import com.marcusscalet.algafood.api.assembler.GroupModelAssembler;
import com.marcusscalet.algafood.api.model.GroupModel;
import com.marcusscalet.algafood.api.openapi.controller.UserGroupControllerOpenApi;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.service.UserRegistrationService;

@RestController
@RequestMapping(value = "/users/{userId}/groups")
public class UserGroupController implements UserGroupControllerOpenApi {

	@Autowired
	private GroupModelAssembler groupModelAssembler;
	
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@GetMapping
	public List<GroupModel> listAll(@PathVariable Long userId){
		User currentUser = userRegistrationService.searchOrFail(userId);
		
		return groupModelAssembler.toCollectionModel(currentUser.getGroup());
	}
	
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociate(@PathVariable Long userId, @PathVariable Long groupId) {
		userRegistrationService.disassociateGroup(userId, groupId);
	}
	
	@PutMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associate(@PathVariable Long userId, @PathVariable Long groupId) {
		userRegistrationService.associateGroup(userId, groupId);
	}
}
