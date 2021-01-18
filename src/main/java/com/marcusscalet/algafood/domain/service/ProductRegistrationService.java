package com.marcusscalet.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.ProductNotFoundException;
import com.marcusscalet.algafood.domain.model.Product;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.repository.ProductRepository;

@Service
public class ProductRegistrationService {

	@Autowired
	private ProductRepository productRepository;

	public Product searchOrFail(Long restaurantId, Long productId) {
		return productRepository.findById(restaurantId, productId)
				.orElseThrow(() -> new ProductNotFoundException(restaurantId, productId));
	}

	public Product save(Product product) {
		return productRepository.save(product);
	}
	public List<Product> findAllProductsByRestaurants(Restaurant restaurant){
		return productRepository.findAllProductsByRestaurant(restaurant);
	}
	
	public List<Product> findAllActiveProducts(Restaurant restaurant){
		return productRepository.findActiveByRestaurant(restaurant);
	}

}
