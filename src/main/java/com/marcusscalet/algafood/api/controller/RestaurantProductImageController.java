package com.marcusscalet.algafood.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.model.input.ProductImageInput;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/image")
public class RestaurantProductImageController {

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void updateImage(@PathVariable Long restaurantId, @PathVariable Long productId,
			@Valid ProductImageInput productImageInput) {

		var nameFile = UUID.randomUUID().toString() + "_" + productImageInput.getFile().getOriginalFilename();

		var fileImage = Path.of("/Users/msbt/Desktop/catalog", nameFile);

		System.out.println(productImageInput.getDescription());
		System.out.println(fileImage);
		System.out.println(productImageInput.getFile().getContentType());
		
		try {
			productImageInput.getFile().transferTo(fileImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
