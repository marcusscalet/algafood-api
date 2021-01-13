package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.OrderedSummaryDTO;
import com.marcusscalet.algafood.domain.model.Ordered;

@Component
public class OrderedSummaryDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public OrderedSummaryDTO toDTO(Ordered ordered) {
		return modelMapper.map(ordered, OrderedSummaryDTO.class);
	}

	public List<OrderedSummaryDTO> toCollectionDTO(List<Ordered> orderedList) {
		return orderedList.stream().map(ordered -> toDTO(ordered)).collect(Collectors.toList());
	}
}
