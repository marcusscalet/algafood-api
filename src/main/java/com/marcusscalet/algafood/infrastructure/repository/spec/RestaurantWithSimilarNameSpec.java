package com.marcusscalet.algafood.infrastructure.repository.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.marcusscalet.algafood.domain.model.Restaurant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestaurantWithSimilarNameSpec implements Specification<Restaurant> {

	private static final long serialVersionUID = 1L;

	private String name;
	
	//Este especification funciona como um filtro para buscarmos restaurants por nome
	@Override
	public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {

		return builder.like(root.get("name"), "%" + name + "%");
	}
	
	

}
