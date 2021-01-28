package com.marcusscalet.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.ProductImageNotFoundException;
import com.marcusscalet.algafood.domain.model.ProductImage;
import com.marcusscalet.algafood.domain.repository.ProductRepository;
import com.marcusscalet.algafood.domain.service.ImageStorageService.NewImage;

@Service
public class ProductImageCatalogService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ImageStorageService imageStorageService;

	public ProductImage searchOrFail(Long restaurantId, Long productId) {
		return productRepository.findImageById(restaurantId, productId)
				.orElseThrow(() -> new ProductImageNotFoundException(restaurantId, productId));
	}
	
	@Transactional
	public void remove(Long restaurantId, Long productId) {
		ProductImage image = searchOrFail(restaurantId, productId);
		
		productRepository.delete(image);
		productRepository.flush();
		
		imageStorageService.remove(image.getFileName());
	}
	
	@Transactional
	public ProductImage save(ProductImage image, InputStream dataFile) {
		Long restaurantId = image.getRestaurantId();
		Long productId = image.getProduct().getId();
		
		String newFileName = imageStorageService.generateFileName(image.getFileName());
		String storedFileName = null;
		
		Optional<ProductImage> savedImage = productRepository
				.findImageById(restaurantId, productId);
		
		if (savedImage.isPresent()) {
			storedFileName = savedImage.get().getFileName();
			productRepository.delete(savedImage.get());
		}
		
		image.setFileName(newFileName);
		image =  productRepository.save(image);
		productRepository.flush();
		
		NewImage newImage = NewImage.builder()
				.fileName(image.getFileName())
				.inputStream(dataFile).build();

		if(storedFileName != null) {	
		imageStorageService.remove(storedFileName);
		
		}
		imageStorageService.substitute(storedFileName, newImage);
		
		return image;
	}
}
