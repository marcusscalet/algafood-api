package com.marcusscalet.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.api.v1.controller.RestaurantProductImageController;
import com.marcusscalet.algafood.api.v1.model.ProductImageModel;
import com.marcusscalet.algafood.domain.model.ProductImage;

@Component
public class ProductImageModelAssembler extends RepresentationModelAssemblerSupport<ProductImage, ProductImageModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public ProductImageModelAssembler() {
		super(RestaurantProductImageController.class, ProductImageModel.class);
	}

	@Override
	public ProductImageModel toModel(ProductImage productImage) {
		ProductImageModel fotoProdutoModel = modelMapper.map(productImage, ProductImageModel.class);

		fotoProdutoModel.add(algaLinks.linkToProductImage(productImage.getRestaurantId(), productImage.getProduct().getId()));

		fotoProdutoModel.add(algaLinks.linkToProduct(productImage.getRestaurantId(), productImage.getProduct().getId(), "product"));

		return fotoProdutoModel;
	}
}
