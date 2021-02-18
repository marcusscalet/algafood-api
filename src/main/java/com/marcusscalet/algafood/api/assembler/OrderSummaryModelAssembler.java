package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.AlgaLinks;
import com.marcusscalet.algafood.api.controller.OrderController;
import com.marcusscalet.algafood.api.model.OrderSummaryModel;
import com.marcusscalet.algafood.domain.model.Order;

@Component
public class OrderSummaryModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderSummaryModel>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public OrderSummaryModelAssembler() {
		super(OrderController.class, OrderSummaryModel.class);
	}

	public OrderSummaryModel toModel(Order order) {
		OrderSummaryModel orderSummaryModel = createModelWithId(order.getId(), order);
		modelMapper.map(order, orderSummaryModel);
		
		orderSummaryModel.add(algaLinks.linkToOrders("orders"));
		 
		orderSummaryModel.getRestaurant().add(algaLinks.linkToRestaurant(order.getRestaurant().getId()));
		
		orderSummaryModel.getClient().add(algaLinks.linkToUser(order.getClient().getId()));
		
		return orderSummaryModel;
	}
}
