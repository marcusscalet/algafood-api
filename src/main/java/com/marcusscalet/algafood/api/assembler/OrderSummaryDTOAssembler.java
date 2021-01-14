package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.OrderSummaryDTO;
import com.marcusscalet.algafood.domain.model.Order;

@Component
public class OrderSummaryDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public OrderSummaryDTO toDTO(Order order) {
		return modelMapper.map(order, OrderSummaryDTO.class);
	}

	public List<OrderSummaryDTO> toCollectionDTO(List<Order> ordersList) {
		return ordersList.stream().map(order -> toDTO(order)).collect(Collectors.toList());
	}
}
