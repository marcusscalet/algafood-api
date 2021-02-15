package com.marcusscalet.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.controller.OrderController;
import com.marcusscalet.algafood.api.controller.RestaurantController;
import com.marcusscalet.algafood.api.controller.UserController;
import com.marcusscalet.algafood.api.model.OrderSummaryModel;
import com.marcusscalet.algafood.domain.model.Order;

@Component
public class OrderSummaryModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderSummaryModel>{

	@Autowired
	private ModelMapper modelMapper;

	public OrderSummaryModelAssembler() {
		super(OrderController.class, OrderSummaryModel.class);
	}

	public OrderSummaryModel toModel(Order order) {
		OrderSummaryModel orderSummaryModel = createModelWithId(order.getId(), order);
		modelMapper.map(order, orderSummaryModel);
		
		orderSummaryModel.add(linkTo(OrderController.class).withRel("orders"));
		 
		orderSummaryModel.getRestaurant().add(linkTo(methodOn(RestaurantController.class)
				.find(order.getRestaurant().getId())).withSelfRel());
		
		orderSummaryModel.getClient().add(linkTo(methodOn(UserController.class)
				.find(order.getClient().getId())).withSelfRel());
		
		return orderSummaryModel;
	}
}
