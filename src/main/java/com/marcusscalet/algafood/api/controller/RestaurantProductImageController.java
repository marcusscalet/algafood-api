package com.marcusscalet.algafood.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.marcusscalet.algafood.api.assembler.ProductImageDTOAssembler;
import com.marcusscalet.algafood.api.model.ProductImageDTO;
import com.marcusscalet.algafood.api.model.input.ProductImageInput;
import com.marcusscalet.algafood.domain.model.Product;
import com.marcusscalet.algafood.domain.model.ProductImage;
import com.marcusscalet.algafood.domain.service.ProductImageCatalogService;
import com.marcusscalet.algafood.domain.service.ProductRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/image")
public class RestaurantProductImageController {

	@Autowired
	private ProductImageDTOAssembler productImageDTOAssembler;

	@Autowired
	private ProductImageCatalogService productImageCatalogService;

	@Autowired
	private ProductRegistrationService productRegistrationService;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductImageDTO updateImage(@PathVariable Long restaurantId, @PathVariable Long productId,
			@Valid ProductImageInput productImageInput) throws IOException {
	
		Product product = productRegistrationService.searchOrFail(restaurantId, productId);
		
		MultipartFile file = productImageInput.getFile();
		
		ProductImage image = new ProductImage();
		image.setProduct(product);
		image.setDescription(productImageInput.getDescription());
		image.setContentType(file.getContentType());
		image.setSize(file.getSize());
		image.setFileName(file.getOriginalFilename());
		
		ProductImage savedImage = productImageCatalogService.save(image, file.getInputStream());
		
		return productImageDTOAssembler.toDTO(savedImage);
	}

}
