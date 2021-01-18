package com.marcusscalet.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marcusscalet.algafood.domain.model.Product;
import com.marcusscalet.algafood.domain.model.Restaurant;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query("from Product where restaurant.id = :restaurant and id = :product")
	Optional<Product> findById(@Param("restaurant") Long restaurantId, @Param("product") Long productId);
	
	List<Product> findAllProductsByRestaurant(Restaurant restaurant);
	
	@Query("from Product p where p.active = true and p.restaurant = :restaurant")
	List<Product> findActiveByRestaurant(Restaurant restaurant);
}
