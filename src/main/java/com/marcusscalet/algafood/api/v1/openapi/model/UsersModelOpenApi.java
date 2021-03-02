package com.marcusscalet.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.marcusscalet.algafood.api.v1.model.UserModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("UsersModel")
@Data
public class UsersModelOpenApi {

	private UsersEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("UsersEmbeddedModel")
    @Data
    public class UsersEmbeddedModelOpenApi {
        
        private List<UserModel> users;
        
    }   
} 