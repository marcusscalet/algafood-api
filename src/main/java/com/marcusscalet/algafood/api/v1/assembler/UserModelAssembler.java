package com.marcusscalet.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.api.v1.controller.UserController;
import com.marcusscalet.algafood.api.v1.model.UserModel;
import com.marcusscalet.algafood.domain.model.User;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel>{

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UserModelAssembler() {
		super(UserController.class, UserModel.class);
	}
	
	public UserModel toModel(User user) {
		UserModel userModel = createModelWithId(user.getId(), user);
		modelMapper.map(user, userModel);

		userModel.add(algaLinks.linkToUsers("users"));
		
		userModel.add(algaLinks.linkToUserGroups(user.getId(), "users-groups"));
		
		return userModel;
	}
	
	@Override
	public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToUsers());
	}
}
