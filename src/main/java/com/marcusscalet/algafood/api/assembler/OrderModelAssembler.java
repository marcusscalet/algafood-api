package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.AlgaLinks;
import com.marcusscalet.algafood.api.controller.OrderController;
import com.marcusscalet.algafood.api.model.OrderModel;
import com.marcusscalet.algafood.domain.model.Order;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderModel>{

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private ModelMapper modelMapper;
	
	public OrderModelAssembler() {
		super(OrderController.class, OrderModel.class);
	}

	@Override
	public OrderModel toModel(Order order) {
		OrderModel orderModel = createModelWithId(order.getCode(), order);
		modelMapper.map(order, orderModel);
		
		orderModel.add(algaLinks.linkToOrders("orders"));
		
		if(order.canBeConfirmed()) {
			orderModel.add(algaLinks.linkToConfirmationOrder(order.getCode(), "confirmed"));
		}
		
		if(order.canBeCancelled()) {
			orderModel.add(algaLinks.linkToCancellationOrder(order.getCode(), "cancelled"));
		}
		
		if(order.canBeDelivered()) {
			orderModel.add(algaLinks.linkToDeliveredOrder(order.getCode(), "delivered"));
		}
		
		orderModel.getRestaurant().add(
				algaLinks.linkToRestaurant(order.getRestaurant().getId()));
		
		orderModel.getClient().add(
				algaLinks.linkToUser(order.getClient().getId()));
		
		orderModel.getPaymentMethod().add(
				algaLinks.linkToPaymentMethod(order.getPaymentMethod().getId()));
		
		orderModel.getDeliveryAddress().getCity().add(
				algaLinks.linkToCity(order.getDeliveryAddress().getCity().getId()));
        
		orderModel.getItens().forEach(item -> {
            item.add(algaLinks.linkToProduct(
            		orderModel.getRestaurant().getId(), item.getProductId(), "product"));
        });
        
		return orderModel;
	}
}
