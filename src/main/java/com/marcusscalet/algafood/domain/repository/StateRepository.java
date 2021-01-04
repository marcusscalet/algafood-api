package com.marcusscalet.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.marcusscalet.algafood.domain.model.State;

@Repository
public interface StateRepository extends CustomJpaRepository<State, Long> {

}
