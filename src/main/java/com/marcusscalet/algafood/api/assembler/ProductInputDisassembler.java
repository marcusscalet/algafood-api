package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.input.ProductInput;
import com.marcusscalet.algafood.domain.model.Product;

@Component
public class ProductInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Product toDomainObject(ProductInput productInput) {
		return modelMapper.map(productInput, Product.class);
	}

	public void copyToDomainObject(ProductInput productInput, Product product) {
		
		modelMapper.map(productInput, product);
	}

}