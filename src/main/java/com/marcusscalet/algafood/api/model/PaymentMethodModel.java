package com.marcusscalet.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "paymentMethods")
@Getter
@Setter
public class PaymentMethodModel extends RepresentationModel<PaymentMethodModel>{

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Cartão de crédito", required = true)
	private String description;

}
