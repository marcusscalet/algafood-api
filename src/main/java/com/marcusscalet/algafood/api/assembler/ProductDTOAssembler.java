package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.ProductDTO;
import com.marcusscalet.algafood.domain.model.Product;

@Component
public class ProductDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProductDTO toDTO(Product product) {
		return modelMapper.map(product, ProductDTO.class);
	}
	
	public List<ProductDTO> toCollectionDTO(List<Product> products){
		return products.stream().map(product -> toDTO(product)).collect(Collectors.toList());
	}
}
