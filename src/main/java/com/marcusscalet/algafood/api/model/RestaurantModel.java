package com.marcusscalet.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantModel  extends RepresentationModel<RestaurantModel> {

	@ApiModelProperty(example = "1")
//	@JsonView({ RestaurantView.Summary.class, RestaurantView.OnlyName.class} )
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
//	@JsonView({ RestaurantView.Summary.class, RestaurantView.OnlyName.class} )
	private String name;
	
	@ApiModelProperty(example = "12.00")
//	@JsonView(RestaurantView.Summary.class)
	private BigDecimal shippingFee;
	
//	@JsonView(RestaurantView.Summary.class)
	private CuisineModel cuisine;
	
	private Boolean active;
	
	private Boolean open;
	
	private AddressModel address;
}
