package com.marcusscalet.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.input.OrderInput;
import com.marcusscalet.algafood.domain.model.Order;

@Component
public class OrderInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Order toDomainObject(OrderInput orderInput) {
		return modelMapper.map(orderInput, Order.class);
	}
	
	public void copyToDomainObject(OrderInput orderInput, Order order) {
		modelMapper.map(orderInput, order);
	}
}
