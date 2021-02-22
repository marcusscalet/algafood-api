package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.marcusscalet.algafood.api.AlgaLinks;
import com.marcusscalet.algafood.api.assembler.ProductInputDisassembler;
import com.marcusscalet.algafood.api.assembler.ProductModelAssembler;
import com.marcusscalet.algafood.api.model.ProductModel;
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
	private ProductModelAssembler productModelAssembler;

	@Autowired
	private ProductInputDisassembler productInputDisassembler;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Override
	@GetMapping
	public CollectionModel<ProductModel> listAll(@PathVariable Long restaurantId, @RequestParam(required = false, defaultValue = "false") Boolean includeInactive) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);
		
		List<Product> products = null;
		
		if(includeInactive) {
			products = productRegistrationService.findAllProductsByRestaurants(restaurant);
		} else {
			products = productRegistrationService.findAllActiveProducts(restaurant);
		}
		
		return productModelAssembler.toCollectionModel(products)
				.add(algaLinks.linkToProducts(restaurantId));
	}

	@Override
	@GetMapping("/{productId}")
	public ProductModel findById(@PathVariable Long productId, @PathVariable Long restaurantId) {
		Product product = productRegistrationService.searchOrFail(restaurantId, productId);
		
		return productModelAssembler.toModel(product);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductModel add(@PathVariable Long restaurantId, @Valid @RequestBody ProductInput productInput) {
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(restaurantId);
		
		Product product = productInputDisassembler.toDomainObject(productInput);
		product.setRestaurant(restaurant);
		
		product = productRegistrationService.save(product);
		
		return productModelAssembler.toModel(product);
	}

	@Override
	@PutMapping("/{productId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ProductModel update(@PathVariable Long productId, @PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
		
		Product currentProduct = productRegistrationService.searchOrFail(restaurantId, productId);
		
		productInputDisassembler.copyToDomainObject(productInput, currentProduct);
		
		currentProduct = productRegistrationService.save(currentProduct);
		
		return productModelAssembler.toModel(currentProduct);
	}
}
