package com.marcusscalet.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.marcusscalet.algafood.domain.model.Ordered;

public interface OrderedRepository extends CustomJpaRepository<Ordered, Long>{

	@Query("from Ordered o join fetch o.client join fetch o.restaurant r join fetch r.cuisine")
	public List<Ordered> findAll();
}
