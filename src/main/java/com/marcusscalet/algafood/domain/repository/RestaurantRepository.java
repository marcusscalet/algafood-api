package com.marcusscalet.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marcusscalet.algafood.domain.model.Restaurant;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries,
		JpaSpecificationExecutor<Restaurant> {

	@Query("from Restaurant r join fetch r.cuisine")
	List<Restaurant> findAll();
	
	List<Restaurant> queryByShippingFeeBetween(BigDecimal initialShippingFee, BigDecimal finalShippingFee);

	List<Restaurant> consultByName(String name, @Param("id") Long cuisineId);

	Optional<Restaurant> findFirstByNameContaining(String name);

	List<Restaurant> findTop2ByNameContaining(String name);

	int countByCuisineId(Long cuisineId);

}