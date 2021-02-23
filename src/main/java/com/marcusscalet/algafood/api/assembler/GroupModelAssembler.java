package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.AlgaLinks;
import com.marcusscalet.algafood.api.controller.GroupController;
import com.marcusscalet.algafood.api.model.GroupModel;
import com.marcusscalet.algafood.domain.model.Group;

@Component
public class GroupModelAssembler extends RepresentationModelAssemblerSupport<Group, GroupModel>{

	@Autowired
	private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;
    
    public GroupModelAssembler() {
        super(GroupController.class, GroupModel.class);
    }
    
    @Override
    public GroupModel toModel(Group groups) {
        GroupModel grupoModel = createModelWithId(groups.getId(), groups);
        modelMapper.map(groups, grupoModel);
        
        grupoModel.add(algaLinks.linkToGroups("groups"));
        
        grupoModel.add(algaLinks.linkToGroupPermissions(groups.getId(), "permissions"));
        
        return grupoModel;
    }
    
    @Override
    public CollectionModel<GroupModel> toCollectionModel(Iterable<? extends Group> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToGroups());
    }            
}        