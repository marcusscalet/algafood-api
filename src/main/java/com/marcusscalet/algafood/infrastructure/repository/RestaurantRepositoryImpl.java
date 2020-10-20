package com.marcusscalet.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.repository.RestaurantRepositoryQueries;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Restaurant> find(String name, BigDecimal initialFreightRate, BigDecimal finalFreightRate) {

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
		Root<Restaurant> root = criteria.from(Restaurant.class);

		var predicates = new ArrayList<Predicate>();

		if (StringUtils.hasText(name)) {
			predicates.add(builder.like(root.get("name"), "%" + name + "%"));
		}

		if (initialFreightRate != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("freightRate"), initialFreightRate));
		}

		if (finalFreightRate != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("freightRate"), finalFreightRate));
		}

		// Convertendo lista de predicates em array de predicates passando uma instância
		// de um array vazio
		criteria.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Restaurant> query = em.createQuery(criteria);
		return query.getResultList();

	}
}
