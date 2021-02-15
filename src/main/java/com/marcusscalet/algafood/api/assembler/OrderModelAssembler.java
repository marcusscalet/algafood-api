package com.marcusscalet.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.controller.CityController;
import com.marcusscalet.algafood.api.controller.OrderController;
import com.marcusscalet.algafood.api.controller.PaymentMethodController;
import com.marcusscalet.algafood.api.controller.RestaurantController;
import com.marcusscalet.algafood.api.controller.RestaurantProductController;
import com.marcusscalet.algafood.api.controller.UserController;
import com.marcusscalet.algafood.api.model.OrderModel;
import com.marcusscalet.algafood.domain.model.Order;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderModel>{


	@Autowired
	private ModelMapper modelMapper;
	
	public OrderModelAssembler() {
		super(OrderController.class, OrderModel.class);
	}

	public OrderModel toModel(Order order) {
		OrderModel orderModel = createModelWithId(order.getId(), order);
		modelMapper.map(order, orderModel);
		
		orderModel.add(linkTo(OrderController.class).withRel("orders"));
		
		orderModel.getRestaurant().add(linkTo(methodOn(RestaurantController.class)
				.find(order.getRestaurant().getId())).withSelfRel());
		
		orderModel.getClient().add(linkTo(methodOn(UserController.class)
				.find(order.getClient().getId())).withSelfRel());
		
	    // Passamos null no segundo argumento, porque é indiferente para a
        // construção da URL do recurso de forma de pagamento
		orderModel.getPaymentMethod().add(linkTo(methodOn(PaymentMethodController.class)
                .find(order.getPaymentMethod().getId(), null)).withSelfRel());
		
		orderModel.getDeliveryAdrress().getCity().add(linkTo(methodOn(CityController.class)
                .find(order.getDeliveryAddress().getCity().getId())).withSelfRel());
        
		orderModel.getItens().forEach(item -> {
            item.add(linkTo(methodOn(RestaurantProductController.class)
                    .findById(orderModel.getRestaurant().getId(), item.getProductId()))
                    .withRel("produto"));
        });
        
		return orderModel;
	}
}
