package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.input.OrderedInput;
import com.marcusscalet.algafood.domain.model.Ordered;

@Component
public class OrderedInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Ordered toDomainObject(OrderedInput orderedInput) {
		return modelMapper.map(orderedInput, Ordered.class);
	}
	
	public void copyToDomainObject(OrderedInput orderedInput, Ordered ordered) {
		modelMapper.map(orderedInput, ordered);
	}
}
