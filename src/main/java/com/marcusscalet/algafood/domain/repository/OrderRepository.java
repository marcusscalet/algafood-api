package com.marcusscalet.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.marcusscalet.algafood.domain.model.Order;

public interface OrderRepository extends 
CustomJpaRepository<Order, Long>,
JpaSpecificationExecutor<Order> {

	Optional<Order> findByCode(String code);

	@Query("from order_ o join fetch o.client join fetch o.restaurant r join fetch r.cuisine")
	public List<Order> findAll();
}
