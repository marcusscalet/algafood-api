package com.marcusscalet.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.marcusscalet.algafood.domain.model.StatusOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "orders")
@Getter
@Setter
public class OrderSummaryModel extends RepresentationModel<OrderSummaryModel> {

	@ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
	private String code;

	@ApiModelProperty(example = "298.90")
	private BigDecimal subtotal;
	
	@ApiModelProperty(example = "10.00")
	private BigDecimal shippingFee;

	@ApiModelProperty(example = "308.90")
	private BigDecimal totalCost;

	@ApiModelProperty(example = "CREATED")
	private StatusOrder status;

	@ApiModelProperty(example = "2019-12-01T20:34:04Z", dataType = "ISO_OFFSET_DATE_TIME")
	private OffsetDateTime creationDate;

	private RestaurantSummaryModel restaurant;

	private UserModel client;
}
