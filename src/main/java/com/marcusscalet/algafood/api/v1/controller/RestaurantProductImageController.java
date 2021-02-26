package com.marcusscalet.algafood.api.v1.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.marcusscalet.algafood.api.model.input.ProductImageInput;
import com.marcusscalet.algafood.api.openapi.controller.RestaurantProductImageControllerOpenApi;
import com.marcusscalet.algafood.api.v1.assembler.ProductImageModelAssembler;
import com.marcusscalet.algafood.api.v1.model.ProductImageModel;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.Product;
import com.marcusscalet.algafood.domain.model.ProductImage;
import com.marcusscalet.algafood.domain.service.ImageStorageService;
import com.marcusscalet.algafood.domain.service.ImageStorageService.RecoveredImage;
import com.marcusscalet.algafood.domain.service.ProductImageCatalogService;
import com.marcusscalet.algafood.domain.service.ProductRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/image")
public class RestaurantProductImageController implements RestaurantProductImageControllerOpenApi{

	@Autowired
	private ProductImageModelAssembler productImageModelAssembler;

	@Autowired
	private ProductImageCatalogService productImageCatalogService;

	@Autowired
	private ProductRegistrationService productRegistrationService;

	@Autowired
	private ImageStorageService imageStorageService;
	
//	@Override
//	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ProductImageModel updateImage(@PathVariable Long restaurantId, @PathVariable Long productId,
//			@Valid ProductImageInput productImageInput,
//			@RequestPart(required = true) MultipartFile file) throws IOException {


	@Override
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductImageModel updateImage(@PathVariable Long restaurantId, @PathVariable Long productId, 
			@RequestPart(required = true) MultipartFile file, 
			@Valid ProductImageInput productImageInput) throws IOException {
	
		Product product = productRegistrationService.searchOrFail(restaurantId, productId);

//		MultipartFile file = productImageInput.getFile();

		ProductImage image = new ProductImage();
		image.setProduct(product);
		image.setDescription(productImageInput.getDescription());
		image.setContentType(file.getContentType());
		image.setSize(file.getSize());
		image.setFileName(file.getOriginalFilename());

		ProductImage savedImage = productImageCatalogService.save(image, file.getInputStream());

		return productImageModelAssembler.toModel(savedImage);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductImageModel searchImage(@PathVariable Long restaurantId, @PathVariable Long productId) {

		ProductImage fds = productImageCatalogService.searchOrFail(restaurantId, productId);

		return productImageModelAssembler.toModel(fds);
	}

	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> showImage(@PathVariable Long restaurantId, @PathVariable Long productId,
			@RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {

		try {
			ProductImage image = productImageCatalogService.searchOrFail(restaurantId, productId);

			MediaType mediaTypeImage = MediaType.parseMediaType(image.getContentType());
			List<MediaType> acceptedMediaTypes = MediaType.parseMediaTypes(acceptHeader);

			checkImageCompatibility(acceptedMediaTypes, mediaTypeImage);

			RecoveredImage recoveredImage = imageStorageService.recover(image.getFileName());

			if (recoveredImage.hasUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, recoveredImage.getUrl())
						.build();
			} else {

				return ResponseEntity.ok().contentType(mediaTypeImage)
						.body(new InputStreamResource(recoveredImage.getInputStream()));
			}
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long restaurantId, @PathVariable Long productId) {
		productImageCatalogService.remove(restaurantId, productId);
	}

	private void checkImageCompatibility(List<MediaType> acceptedMediaTypes, MediaType mediaTypeImage)
			throws HttpMediaTypeNotAcceptableException {

		boolean compatible = acceptedMediaTypes.stream()
				.anyMatch(acceptedMediaType -> acceptedMediaType.isCompatibleWith(mediaTypeImage));

		if (!compatible) {
			throw new HttpMediaTypeNotAcceptableException(acceptedMediaTypes);
		}
	}

}
