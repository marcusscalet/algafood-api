package com.marcusscalet.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.GroupModel;
import com.marcusscalet.algafood.domain.model.Group;

@Component
public class GroupModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public GroupModel toModel(Group group) {
		return modelMapper.map(group, GroupModel.class);
	}
	
	public List<GroupModel> toCollectionModel(Collection<Group> groups){
		return groups.stream().map(group -> toModel(group)).collect(Collectors.toList());
	}
}
