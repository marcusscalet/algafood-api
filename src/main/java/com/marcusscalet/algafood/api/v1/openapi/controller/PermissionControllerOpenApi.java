package com.marcusscalet.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.marcusscalet.algafood.api.v1.model.PermissionModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissions")
public interface PermissionControllerOpenApi {

	@ApiOperation("Lista as permissões")
    CollectionModel<PermissionModel> listAll();
    
}
