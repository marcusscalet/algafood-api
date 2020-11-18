package com.marcusscalet.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.marcusscalet.algafood.domain.model.Restaurant;

public interface RestaurantRepositoryQueries {

	List<Restaurant> find(String name,
			BigDecimal initialShippingFee, BigDecimal finalShippingFee);

	List<Restaurant> findWithFreeShippingRate(String name);

}