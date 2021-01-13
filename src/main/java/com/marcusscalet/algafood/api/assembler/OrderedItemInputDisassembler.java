package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.input.OrderedItemInput;
import com.marcusscalet.algafood.domain.model.OrderedItem;

@Component
public class OrderedItemInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public OrderedItem toDomainObject(OrderedItemInput orderedItemInput) {
		return modelMapper.map(orderedItemInput, OrderedItem.class);
	}
	
	public void copyToDomainObject(OrderedItemInput orderedItemInput, OrderedItem orderedItem) {
		modelMapper.map(orderedItemInput, orderedItem);
	}
}
