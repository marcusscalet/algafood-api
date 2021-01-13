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

import com.marcusscalet.algafood.api.assembler.OrderedDTOAssembler;
import com.marcusscalet.algafood.api.assembler.OrderedInputDisassembler;
import com.marcusscalet.algafood.api.assembler.OrderedSummaryDTOAssembler;
import com.marcusscalet.algafood.api.model.OrderedDTO;
import com.marcusscalet.algafood.api.model.OrderedSummaryDTO;
import com.marcusscalet.algafood.api.model.input.OrderedInput;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.Ordered;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.service.OrderedRegistrationService;

@RestController
@RequestMapping(value = "/ordered")
public class OrderedController {

	@Autowired
	private OrderedRegistrationService orderedRegistrationService;

	@Autowired
	private OrderedSummaryDTOAssembler orderedSummaryDTOAssembler;

	@Autowired
	private OrderedDTOAssembler orderedDTOAssembler;

	@Autowired
	private OrderedInputDisassembler orderedInputDisassembler;

	@GetMapping
	public List<OrderedSummaryDTO> listAll() {
		return orderedSummaryDTOAssembler.toCollectionDTO(orderedRegistrationService.listAll());
	}

	@GetMapping("/{orderedId}")
	public OrderedDTO find(@PathVariable Long orderedId) {
		Ordered ordered = orderedRegistrationService.searchOrFail(orderedId);
		
		return orderedDTOAssembler.toDTO(ordered);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderedDTO save(@RequestBody @Valid OrderedInput orderedInput) {
		try {
			Ordered ordered = orderedInputDisassembler.toDomainObject(orderedInput);

			// TODO pegar usuário autenticado
			ordered.setClient(new User());
			ordered.getClient().setId(1L);

			ordered = orderedRegistrationService.generateOrder(ordered);

			return orderedDTOAssembler.toDTO(ordered);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
