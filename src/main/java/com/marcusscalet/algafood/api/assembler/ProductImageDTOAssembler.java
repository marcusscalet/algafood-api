package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.ProductImageDTO;
import com.marcusscalet.algafood.domain.model.ProductImage;

@Component
public class ProductImageDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProductImageDTO toDTO(ProductImage productImage) {
		return modelMapper.map(productImage, ProductImageDTO.class);
	}
}
