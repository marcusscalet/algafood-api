package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.marcusscalet.algafood.api.assembler.OrderDTOAssembler;
import com.marcusscalet.algafood.api.assembler.OrderInputDisassembler;
import com.marcusscalet.algafood.api.assembler.OrderSummaryDTOAssembler;
import com.marcusscalet.algafood.api.model.OrderDTO;
import com.marcusscalet.algafood.api.model.OrderSummaryDTO;
import com.marcusscalet.algafood.api.model.input.OrderInput;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.Order;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.service.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

	@Autowired
	private OrderService orderRegistrationService;

	@Autowired
	private OrderSummaryDTOAssembler orderSummaryDTOAssembler;

	@Autowired
	private OrderDTOAssembler orderDTOAssembler;

	@Autowired
	private OrderInputDisassembler orderInputDisassembler;

	@GetMapping
	public MappingJacksonValue listAll(@RequestParam(required = false)String fields) {
		List<Order> orders = orderRegistrationService.listAll();
		List<OrderSummaryDTO> ordersDTO = orderSummaryDTOAssembler.toCollectionDTO(orders);

		MappingJacksonValue ordersWrapper = new MappingJacksonValue(ordersDTO);
		
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.serializeAll());
		
		ordersWrapper.setFilters(filterProvider);
		
		return ordersWrapper;
	}
	
//	@GetMapping
//	public List<OrderSummaryDTO> listAll() {
//		return orderSummaryDTOAssembler.toCollectionDTO(orderRegistrationService.listAll());
//	}

	@GetMapping("/{orderCode}")
	public OrderDTO find(@PathVariable String orderCode) {
		Order order = orderRegistrationService.searchOrFail(orderCode);
		
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

			order = orderRegistrationService.generateOrder(order);

			return orderDTOAssembler.toDTO(order);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
