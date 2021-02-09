package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.assembler.ProductDTOAssembler;
import com.marcusscalet.algafood.api.assembler.ProductInputDisassembler;
import com.marcusscalet.algafood.api.model.ProductDTO;
import com.marcusscalet.algafood.api.model.input.ProductInput;
import com.marcusscalet.algafood.api.openapi.controller.RestaurantProductControllerOpenApi;
import com.marcusscalet.algafood.domain.model.Product;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.service.ProductRegistrationService;
import com.marcusscalet.algafood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/products")
public class RestaurantProductController implements RestaurantProductControllerOpenApi{

	@Autowired
	private ProductRegistrationService productRegistrationService;

	@Autowired
	private ProductDTOAssembler productDTOAssembler;

	@Autowired
	private ProductInputDisassembler productInputDisassembler;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@GetMapping
	public List<ProductDTO> listAll(@PathVariable Long restaurantId, @RequestParam(required = false) boolean includeInactive) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);
		
		List<Product> products = productRegistrationService.findAllActiveProducts(restaurant);
		
		if(includeInactive) {
			products = productRegistrationService.findAllProductsByRestaurants(restaurant);
		} else {
			products = productRegistrationService.findAllActiveProducts(restaurant);
		}
		return productDTOAssembler.toCollectionDTO(products);
	}

	@GetMapping("/{productId}")
	public ProductDTO findById(@PathVariable Long productId, @PathVariable Long restaurantId) {
		Product product = productRegistrationService.searchOrFail(restaurantId, productId);
		
		return productDTOAssembler.toDTO(product);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDTO add(@PathVariable Long restaurantId, @Valid @RequestBody ProductInput productInput) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);
		
		Product product = productInputDisassembler.toDomainObject(productInput);
		product.setRestaurant(restaurant);
		
		product = productRegistrationService.save(product);
		
		return productDTOAssembler.toDTO(product);
	}

	@PutMapping("/{productId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ProductDTO update(@PathVariable Long productId, @PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
		
		Product currentProduct = productRegistrationService.searchOrFail(restaurantId, productId);
		
		productInputDisassembler.copyToDomainObject(productInput, currentProduct);
		
		currentProduct = productRegistrationService.save(currentProduct);
		
		return productDTOAssembler.toDTO(currentProduct);
	}
}
