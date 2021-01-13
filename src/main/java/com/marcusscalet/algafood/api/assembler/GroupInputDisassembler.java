package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.input.GroupInput;
import com.marcusscalet.algafood.domain.model.Group;

@Component
public class GroupInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Group toDomainObject(GroupInput groupInput) {
		return modelMapper.map(groupInput, Group.class);
	}

	public void copyToDomainObject(GroupInput groupInput, Group group) {
		modelMapper.map(groupInput, group);
	}

}