package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.marcusscalet.algafood.api.assembler.OrderDTOAssembler;
import com.marcusscalet.algafood.api.assembler.OrderInputDisassembler;
import com.marcusscalet.algafood.api.assembler.OrderSummaryDTOAssembler;
import com.marcusscalet.algafood.api.model.OrderDTO;
import com.marcusscalet.algafood.api.model.OrderSummaryDTO;
import com.marcusscalet.algafood.api.model.input.OrderInput;
import com.marcusscalet.algafood.core.data.PageableTranslator;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.Order;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.repository.filter.OrderFilter;
import com.marcusscalet.algafood.domain.service.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderSummaryDTOAssembler orderSummaryDTOAssembler;

	@Autowired
	private OrderDTOAssembler orderDTOAssembler;

	@Autowired
	private OrderInputDisassembler orderInputDisassembler;

	@GetMapping
	public Page<OrderSummaryDTO> search(OrderFilter filter, 
			@PageableDefault(size = 10) Pageable pageable) {
		pageable = translatePageable(pageable);
		
		Page<Order> ordersPage= orderService.findAll(filter, pageable);
		
		List<OrderSummaryDTO> ordersList = orderSummaryDTOAssembler.toCollectionDTO(ordersPage.getContent());
		
		Page<OrderSummaryDTO> ordersDTOPage = new PageImpl<OrderSummaryDTO>(ordersList, pageable, ordersPage.getTotalElements());
		
		return ordersDTOPage;
	}

	@GetMapping("/{orderCode}")
	public OrderDTO find(@PathVariable String orderCode) {
		Order order = orderService.searchOrFail(orderCode);

		return orderDTOAssembler.toDTO(order);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO save(@RequestBody @Valid OrderInput orderInput) {
		try {
			Order order = orderInputDisassembler.toDomainObject(orderInput);

			// TODO pegar usuário autenticado
			order.setClient(new User());
			order.getClient().setId(1L);
			
			order = orderService.generateOrder(order);

			return orderDTOAssembler.toDTO(order);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	private Pageable translatePageable(Pageable apiPageable) {
		var mapper = ImmutableMap.of(
				"code", "code",
				"clientName", "client.name",
				"restaurant.name", "restaurant.name",
				"totalCost", "totalCost"
			);
		
		return PageableTranslator.translate(apiPageable, mapper);
	}
}
