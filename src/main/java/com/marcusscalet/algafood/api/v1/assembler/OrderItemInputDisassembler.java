package com.marcusscalet.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v1.model.input.OrderItemInput;
import com.marcusscalet.algafood.domain.model.OrderItem;

@Component
public class OrderItemInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public OrderItem toDomainObject(OrderItemInput orderItemInput) {
		return modelMapper.map(orderItemInput, OrderItem.class);
	}
	
	public void copyToDomainObject(OrderItemInput orderItemInput, OrderItem orderItem) {
		modelMapper.map(orderItemInput, orderItem);
	}
}
