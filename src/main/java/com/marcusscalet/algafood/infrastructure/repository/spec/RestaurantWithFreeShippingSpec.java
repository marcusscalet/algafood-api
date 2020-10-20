package com.marcusscalet.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.marcusscalet.algafood.domain.model.Restaurant;

public class RestaurantWithFreeShippingSpec implements Specification<Restaurant> {

	private static final long serialVersionUID = 1L;

	//Este especification funciona como um filtro para buscarmos restaurants com frete grátis
	@Override
	public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {

		return builder.equal(root.get("freightRate"), BigDecimal.ZERO);
	}

}
