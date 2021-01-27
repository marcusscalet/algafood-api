package com.marcusscalet.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.marcusscalet.algafood.domain.model.ProductImage;
import com.marcusscalet.algafood.domain.repository.ProductRepositoryQueries;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public ProductImage save(ProductImage image) {
		return manager.merge(image);
	}

	@Transactional
	@Override
	public void delete(ProductImage image) {
		manager.remove(image);
	}
}
