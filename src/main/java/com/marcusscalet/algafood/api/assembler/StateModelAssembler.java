package com.marcusscalet.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.controller.StateController;
import com.marcusscalet.algafood.api.model.StateModel;
import com.marcusscalet.algafood.domain.model.State;

@Component
public class StateModelAssembler extends RepresentationModelAssemblerSupport<State, StateModel>{

	@Autowired
	private ModelMapper modelMapper;
	
	public StateModelAssembler() {
		super(StateController.class, StateModel.class);
	}

	public StateModel toModel(State state) {
		StateModel stateModel = createModelWithId(state.getId(), state);
		modelMapper.map(state, stateModel);
		
		stateModel.add(linkTo(StateController.class).withRel("states"));
	
		return stateModel;
	}


	@Override
	public CollectionModel<StateModel> toCollectionModel(Iterable<? extends State> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(StateController.class).withSelfRel());
	}
}
