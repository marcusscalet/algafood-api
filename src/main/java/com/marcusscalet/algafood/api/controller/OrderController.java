package com.marcusscalet.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
import com.marcusscalet.algafood.domain.service.IssuePurchaseOrderService;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired
	private IssuePurchaseOrderService orderRegistrationService;

	@Autowired
	private OrderSummaryDTOAssembler orderSummaryDTOAssembler;

	@Autowired
	private OrderDTOAssembler orderDTOAssembler;

	@Autowired
	private OrderInputDisassembler orderInputDisassembler;

	@GetMapping
	public List<OrderSummaryDTO> listAll() {
		return orderSummaryDTOAssembler.toCollectionDTO(orderRegistrationService.listAll());
	}

	@GetMapping("/{orderId}")
	public OrderDTO find(@PathVariable Long orderId) {
		Order order = orderRegistrationService.searchOrFail(orderId);
		
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
