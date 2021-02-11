package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.OrderSummaryModel;
import com.marcusscalet.algafood.domain.model.Order;

@Component
public class OrderSummaryModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public OrderSummaryModel toModel(Order order) {
		return modelMapper.map(order, OrderSummaryModel.class);
	}

	public List<OrderSummaryModel> toCollectionModel(List<Order> ordersList) {
		return ordersList.stream().map(order -> toModel(order)).collect(Collectors.toList());
	}
}
