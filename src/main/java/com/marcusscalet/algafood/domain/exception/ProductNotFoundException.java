package com.marcusscalet.algafood.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String mensagem) {
		super(mensagem);
	}

	public ProductNotFoundException(Long restaurantId, Long productId) {
		this(String.format("Não existe um cadastro de product com código %d para o restaurante de código %d.",
				productId, restaurantId));
	}

}