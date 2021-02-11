package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.ProductImageModel;
import com.marcusscalet.algafood.domain.model.ProductImage;

@Component
public class ProductImageModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProductImageModel toModel(ProductImage productImage) {
		return modelMapper.map(productImage, ProductImageModel.class);
	}
}
