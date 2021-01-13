package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.OrderedDTO;
import com.marcusscalet.algafood.domain.model.Ordered;

@Component
public class OrderedDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public OrderedDTO toDTO(Ordered ordered) {
		return modelMapper.map(ordered, OrderedDTO.class);
	}

	public List<OrderedDTO> toCollectionDTO(List<Ordered> ordered) {
		return ordered.stream().map(order -> toDTO(order)).collect(Collectors.toList());
	}
}
