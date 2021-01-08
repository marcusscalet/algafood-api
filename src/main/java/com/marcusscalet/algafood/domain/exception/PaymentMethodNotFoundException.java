package com.marcusscalet.algafood.domain.exception;

public class PaymentMethodNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PaymentMethodNotFoundException(String mensagem) {
		super(mensagem);
	}

	public PaymentMethodNotFoundException(Long paymentMethodId) {
		this(String.format("Não existe um cadastro de método de pagamento com código %d", paymentMethodId));
	}

}