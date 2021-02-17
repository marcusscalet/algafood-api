package com.marcusscalet.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantOnlyNameModel extends RepresentationModel<RestaurantOnlyNameModel>{
	@ApiModelProperty(example = "1")
    private Long id;
    
    @ApiModelProperty(example = "Thai Gourmet")
    private String name;
}
