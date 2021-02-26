package com.marcusscalet.algafood.api.v1.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.model.input.OrderInput;
import com.marcusscalet.algafood.api.openapi.controller.OrderControllerOpenApi;
import com.marcusscalet.algafood.api.v1.assembler.OrderInputDisassembler;
import com.marcusscalet.algafood.api.v1.assembler.OrderModelAssembler;
import com.marcusscalet.algafood.api.v1.assembler.OrderSummaryModelAssembler;
import com.marcusscalet.algafood.api.v1.model.OrderModel;
import com.marcusscalet.algafood.api.v1.model.OrderSummaryModel;
import com.marcusscalet.algafood.core.data.PageWrapper;
import com.marcusscalet.algafood.core.data.PageableTranslator;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.filter.OrderFilter;
import com.marcusscalet.algafood.domain.model.Order;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.service.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController implements OrderControllerOpenApi {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderSummaryModelAssembler orderSummaryModelAssembler;

	@Autowired
	private OrderModelAssembler orderModelAssembler;

	@Autowired
	private OrderInputDisassembler orderInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Order> pagedResourcesAssembler;

	@GetMapping
	public PagedModel<OrderSummaryModel> search(OrderFilter filter, @PageableDefault(size = 10) Pageable pageable) {
		Pageable pageableTranslated = translatePageable(pageable);
		
		Page<Order> ordersPage = orderService.findAll(filter, pageableTranslated);
		
		ordersPage = new PageWrapper<>(ordersPage, pageable);
		
		return pagedResourcesAssembler.toModel(ordersPage, orderSummaryModelAssembler);
	}

	@GetMapping("/{orderCode}")
	public OrderModel find(@PathVariable String orderCode) {
		Order order = orderService.searchOrFail(orderCode);

		return orderModelAssembler.toModel(order);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderModel save(@RequestBody @Valid OrderInput orderInput) {
		try {
			Order order = orderInputDisassembler.toDomainObject(orderInput);

			// TODO pegar usuário autenticado
			order.setClient(new User());
			order.getClient().setId(1L);
			
			order = orderService.generateOrder(order);

			return orderModelAssembler.toModel(order);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	private Pageable translatePageable(Pageable apiPageable) {
		var mapper = Map.of(
				"code", "code",
				"subtotal", "subtotal",
				"shippingFee", "shippingFee",
				"totalCost", "totalCost",
				"creationDate", "creationDate",
				"clientName", "client.name",
				"client.id", "client.id",
				"restaurant.name", "restaurant.name",
				"restaurant.id", "restaurant.id"
			);
		
		return PageableTranslator.translate(apiPageable, mapper);
	}
}
