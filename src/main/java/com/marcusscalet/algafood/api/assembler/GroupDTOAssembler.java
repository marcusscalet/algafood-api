package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.GroupDTO;
import com.marcusscalet.algafood.domain.model.Group;

@Component
public class GroupDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public GroupDTO toDTO(Group group) {
		return modelMapper.map(group, GroupDTO.class);
	}
	
	public List<GroupDTO> toCollectionDTO(List<Group> groups){
		return groups.stream().map(group -> toDTO(group)).collect(Collectors.toList());
	}
}
