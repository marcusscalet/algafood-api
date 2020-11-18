package com.marcusscalet.algafood.infrastructure.repository;

import static com.marcusscalet.algafood.infrastructure.repository.spec.RestaurantSpecs.withFreeShipping;
import static com.marcusscalet.algafood.infrastructure.repository.spec.RestaurantSpecs.withSimilarNameSpec;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.repository.RestaurantRepository;
import com.marcusscalet.algafood.domain.repository.RestaurantRepositoryQueries;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

	@PersistenceContext
	private EntityManager em;

	@Autowired @Lazy
	private RestaurantRepository restaurantRepository;
	
	@Override
	public List<Restaurant> find(String name, BigDecimal initialShippingFee, BigDecimal finalShippingFee) {

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
		Root<Restaurant> root = criteria.from(Restaurant.class);

		var predicates = new ArrayList<Predicate>();

		if (StringUtils.hasText(name)) {
			predicates.add(builder.like(root.get("name"), "%" + name + "%"));
		}

		if (initialShippingFee != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("shippingFee"), initialShippingFee));
		}

		if (finalShippingFee != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("shippingFee"), finalShippingFee));
		}

		// Convertendo lista de predicates em array de predicates passando uma instância
		// de um array vazio
		criteria.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Restaurant> query = em.createQuery(criteria);
		return query.getResultList();

	}

	@Override
	public List<Restaurant> findWithFreeShippingRate(String name) {
		
		return restaurantRepository.findAll(withFreeShipping()
				.and(withSimilarNameSpec(name)));
	}
}
