package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.AlgaLinks;
import com.marcusscalet.algafood.api.controller.RestaurantProductController;
import com.marcusscalet.algafood.api.model.ProductModel;
import com.marcusscalet.algafood.domain.model.Product;

@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductModel>{

	@Autowired
	private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;
    
    public ProductModelAssembler() {
    	super(RestaurantProductController.class, ProductModel.class);
    }
    
	@Override
	public ProductModel toModel(Product product) {
		ProductModel productModel = createModelWithId(
	            product.getId(), product, product.getRestaurant().getId());
	    
	    modelMapper.map(product, productModel);
	    
	    productModel.add(algaLinks.linkToProducts(product.getRestaurant().getId(), "products"));

	    productModel.add(algaLinks.linkToProductImage(
	            product.getRestaurant().getId(), product.getId(), "image"));
	    
	    return productModel;
	}
	
}
