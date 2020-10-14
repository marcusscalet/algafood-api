package com.marcusscalet.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcusscalet.algafood.domain.model.Cuisine;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long>{

	List<Cuisine> findByName(String nome);

	Optional<Cuisine> findUniqueByName(String name);
	
	boolean existsByName(String name);
}
