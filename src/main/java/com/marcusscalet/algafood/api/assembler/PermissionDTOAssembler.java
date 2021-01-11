package com.marcusscalet.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.PermissionDTO;
import com.marcusscalet.algafood.domain.model.Permission;

@Component
public class PermissionDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PermissionDTO toDTO(Permission permission) {
		return modelMapper.map(permission, PermissionDTO.class);
	}
	
	public List<PermissionDTO> toDTOCollection(Collection<Permission> permissions){
		return permissions.stream().map(permission -> toDTO(permission)).collect(Collectors.toList());
	}
}
