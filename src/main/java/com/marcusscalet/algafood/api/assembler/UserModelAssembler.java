package com.marcusscalet.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.controller.UserController;
import com.marcusscalet.algafood.api.controller.UserGroupController;
import com.marcusscalet.algafood.api.model.UserModel;
import com.marcusscalet.algafood.domain.model.User;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel>{

	public UserModelAssembler() {
		super(UserController.class, UserModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	public UserModel toModel(User user) {
		UserModel userModel = createModelWithId(user.getId(), user);
		modelMapper.map(user, userModel);

		userModel.add(linkTo(UserController.class).withRel("users"));
		
		userModel.add(linkTo(methodOn(UserGroupController.class)
				.listAll(user.getId())).withRel("users-groups"));
		
		return userModel;
	}
	
	@Override
	public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(UserController.class).withSelfRel());
	}
}
