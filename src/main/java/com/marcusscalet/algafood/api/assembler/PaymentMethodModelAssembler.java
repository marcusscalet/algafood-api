package com.marcusscalet.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.PaymentMethodModel;
import com.marcusscalet.algafood.domain.model.PaymentMethod;

@Component
public class PaymentMethodModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PaymentMethodModel toModel(PaymentMethod paymentMethod) {
		return modelMapper.map(paymentMethod, PaymentMethodModel.class);
	}

	public List<PaymentMethodModel> toCollectionModel(Collection<PaymentMethod> paymentMethods) {
		return paymentMethods.stream().map(paymentMethod -> toModel(paymentMethod)).collect(Collectors.toList());
	}
}
