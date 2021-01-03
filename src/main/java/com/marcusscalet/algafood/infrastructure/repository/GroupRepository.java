package com.marcusscalet.algafood.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcusscalet.algafood.domain.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{

}
