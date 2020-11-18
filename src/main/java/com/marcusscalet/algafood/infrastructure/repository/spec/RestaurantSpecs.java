package com.marcusscalet.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.marcusscalet.algafood.domain.model.Restaurant;

public class RestaurantSpecs {

	public static Specification<Restaurant> withFreeShipping() {
		return (root, query, builder) ->
		builder.equal(root.get("shippingFee"), BigDecimal.ZERO);
	}

	public static Specification<Restaurant> withSimilarNameSpec(String name) {
		return (root, query, builder) -> 
		builder.like(root.get("name"), "%" + name + "%");
	}
}
