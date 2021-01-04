package com.marcusscalet.algafood.domain.repository;

import java.util.Optional;

import com.marcusscalet.algafood.domain.model.User;

public interface UserRepository extends CustomJpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
}
