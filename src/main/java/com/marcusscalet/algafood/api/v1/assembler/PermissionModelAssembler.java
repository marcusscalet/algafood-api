package com.marcusscalet.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.api.v1.model.PermissionModel;
import com.marcusscalet.algafood.domain.model.Permission;

@Component
public class PermissionModelAssembler 
implements RepresentationModelAssembler<Permission, PermissionModel>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private AlgaLinks algaLinks;

    @Override
    public PermissionModel toModel(Permission permission) {
    	PermissionModel permissionModel = modelMapper.map(permission, PermissionModel.class);
        return permissionModel;
    }
    
    @Override
    public CollectionModel<PermissionModel> toCollectionModel(Iterable<? extends Permission> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(algaLinks.linkToPermissions());
    }   
}
