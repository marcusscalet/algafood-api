package com.marcusscalet.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.marcusscalet.algafood.domain.model.Order;
import com.marcusscalet.algafood.domain.repository.filter.OrderFilter;

public class OrderSpecs {

	public static Specification<Order> withFilter(OrderFilter filter) {
		return (root, query, builder) -> {
			
			root.fetch("restaurant").fetch("cuisine");
			root.fetch("client");
			
			var predicates = new ArrayList<Predicate>();

			if(filter.getClientId() != null) {
				predicates.add(builder.equal(root.get("client"), filter.getClientId()));
			}
			
			if(filter.getRestaurantId() != null) {
				predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
			}
			
			if(filter.getCreationStartDate() != null) {
				predicates.add(builder.greaterThan(root.get("creationDate"), filter.getCreationStartDate()));
			}
			
			if(filter.getCreationEndDate() != null) {
				predicates.add(builder.lessThan(root.get("creationDate"), filter.getCreationEndDate()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
