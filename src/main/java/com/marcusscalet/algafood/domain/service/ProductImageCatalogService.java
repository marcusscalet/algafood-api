package com.marcusscalet.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.model.ProductImage;
import com.marcusscalet.algafood.domain.repository.ProductRepository;
import com.marcusscalet.algafood.domain.service.ImageStorageService.NewImage;

@Service
public class ProductImageCatalogService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ImageStorageService imageStorageService;

	@Transactional
	public ProductImage save(ProductImage image, InputStream fileUploaded) {
		Long restaurantId = image.getRestaurantId();
		Long productId = image.getProduct().getId();
		
		String newFileName = imageStorageService.generateFileName(image.getFileName());
		String fileNameAlreadySaved = null;
		
		Optional<ProductImage> savedImage = productRepository
				.findImageById(restaurantId, productId);
		
		if (savedImage.isPresent()) {
			fileNameAlreadySaved = savedImage.get().getFileName();
			productRepository.delete(savedImage.get());
		}
		
		image.setFileName(newFileName);
		image =  productRepository.save(image);
		productRepository.flush();
		
		NewImage newImage = NewImage.builder()
				.fileName(image.getFileName())
				.inputStream(fileUploaded).build();

		if(fileNameAlreadySaved != null) {	
		imageStorageService.remove(fileNameAlreadySaved);
		
		}
		imageStorageService.substitute(fileNameAlreadySaved, newImage);
		
		return image;
	}
}
