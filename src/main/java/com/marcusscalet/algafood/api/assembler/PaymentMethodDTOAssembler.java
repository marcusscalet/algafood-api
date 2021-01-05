package com.marcusscalet.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.PaymentMethodDTO;
import com.marcusscalet.algafood.domain.model.PaymentMethod;

@Component
public class PaymentMethodDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PaymentMethodDTO toDTO(PaymentMethod paymentMethod) {
		return modelMapper.map(paymentMethod, PaymentMethodDTO.class);
	}

	public List<PaymentMethodDTO> toCollectionDTO(Collection<PaymentMethod> paymentMethods) {
		return paymentMethods.stream().map(paymentMethod -> toDTO(paymentMethod)).collect(Collectors.toList());
	}
}
