package com.marcusscalet.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcusscalet.algafood.domain.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	List<Restaurant> findByFreightRateBetween(BigDecimal initialFreightRate, BigDecimal finalFreightRate);
	
	List<Restaurant> findByNameContainingAndCuisineId(String name, Long cuisineId);
	
	Optional<Restaurant> findFirstByNameContaining(String name);
	
	List<Restaurant> findTop2ByNameContaining(String name);
	
	int countByCuisineId(Long cuisineId);
}