package com.marcusscalet.algafood.domain.exception;

public class OrderedNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public OrderedNotFoundException(String message) {
		super(message);
	}
	
	public OrderedNotFoundException(Long orderedId) {
		this(String.format("Não existe um cadastro de pedido com código %d", orderedId));
	}

}
