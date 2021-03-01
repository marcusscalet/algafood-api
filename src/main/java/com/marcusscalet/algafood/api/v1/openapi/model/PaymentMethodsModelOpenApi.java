package com.marcusscalet.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.marcusscalet.algafood.api.v1.model.PaymentMethodModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("PaymentMethodsModel")
@Data
public class PaymentMethodsModelOpenApi {

	private PaymentMethodsEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("PaymentMethodsEmbeddedModel")
	@Data
	public class PaymentMethodsEmbeddedModelOpenApi {

		private List<PaymentMethodModel> paymentMethods;

	}
}