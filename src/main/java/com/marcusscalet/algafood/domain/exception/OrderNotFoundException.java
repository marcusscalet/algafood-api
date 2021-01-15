package com.marcusscalet.algafood.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(String orderId) {
		super(String.format("Não existe um cadastro de pedido com código %s", orderId));
	}

}
