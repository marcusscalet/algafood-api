package com.marcusscalet.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

@Relation(collectionRelation = "products")
@Setter
public class ProductModel extends RepresentationModel<ProductModel>{

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Espetinho de Cupim")
	private String name;

	@ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete")
	private String description;

	@ApiModelProperty(example = "12.50")
	private BigDecimal price;
	
	@ApiModelProperty(example = "true")
	private boolean active;
}
