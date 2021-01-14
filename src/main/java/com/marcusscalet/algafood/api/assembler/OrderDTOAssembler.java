package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.OrderDTO;
import com.marcusscalet.algafood.domain.model.Order;

@Component
public class OrderDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public OrderDTO toDTO(Order order) {
		return modelMapper.map(order, OrderDTO.class);
	}

	public List<OrderDTO> toCollectionDTO(List<Order> orders) {
		return orders.stream().map(order -> toDTO(order)).collect(Collectors.toList());
	}
}
