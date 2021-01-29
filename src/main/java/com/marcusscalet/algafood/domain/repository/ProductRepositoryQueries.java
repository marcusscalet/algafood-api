package com.marcusscalet.algafood.domain.repository;

import com.marcusscalet.algafood.domain.model.ProductImage;

public interface ProductRepositoryQueries {

	ProductImage save(ProductImage image);
	
	void remove(ProductImage image);
}
