package com.marcusscalet.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcusscalet.algafood.domain.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
