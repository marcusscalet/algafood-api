package com.marcusscalet.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.openapi.controller.PermissionControllerOpenApi;
import com.marcusscalet.algafood.api.v1.assembler.PermissionModelAssembler;
import com.marcusscalet.algafood.api.v1.model.PermissionModel;
import com.marcusscalet.algafood.domain.model.Permission;
import com.marcusscalet.algafood.domain.repository.PermissionRepository;

@RestController
@RequestMapping(path = "/permissions")
public class PermissionController implements PermissionControllerOpenApi {


    @Autowired
    private PermissionRepository permissionRepository;
    
    @Autowired
    private PermissionModelAssembler permissaoModelAssembler;
    
    @Override
    @GetMapping
    public CollectionModel<PermissionModel> listAll() {
        List<Permission> permissions = permissionRepository.findAll();
        
        return permissaoModelAssembler.toCollectionModel(permissions);
    }   
}
