package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.AlgaLinks;
import com.marcusscalet.algafood.api.controller.PaymentMethodController;
import com.marcusscalet.algafood.api.model.PaymentMethodModel;
import com.marcusscalet.algafood.domain.model.PaymentMethod;

@Component
public class PaymentMethodModelAssembler extends RepresentationModelAssemblerSupport<PaymentMethod, PaymentMethodModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private AlgaLinks algaLinks;
	
	public PaymentMethodModelAssembler() {
		super(PaymentMethodController.class, PaymentMethodModel.class);
	}
	
	@Override
	public PaymentMethodModel toModel(PaymentMethod paymentMethod) {
		PaymentMethodModel paymentMethodModel = createModelWithId(paymentMethod.getId(), paymentMethod);
		
		modelMapper.map(paymentMethod, paymentMethodModel);
		
		paymentMethodModel.add(algaLinks.linkToPaymentMethods("paymentMethods"));
		
		return paymentMethodModel;
	}
	
	@Override
	public CollectionModel<PaymentMethodModel> toCollectionModel(Iterable<? extends PaymentMethod> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToPaymentMethods());
	}
}
