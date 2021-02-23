package com.marcusscalet.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "images")
@Getter
@Setter
public class ProductImageModel extends RepresentationModel<ProductImageModel> {

	@ApiModelProperty(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Prime-Rib.jpg")
	private String fileName;

	@ApiModelProperty(example = "Pizza Vegana")
	private String description;

	@ApiModelProperty(example = "image/jpeg")
	private String contentType;
	
	@ApiModelProperty(example = "202912")
	private Long size;
}
