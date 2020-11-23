package com.marcusscalet.algafood.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public RestaurantNotFoundException(String mensagem) {
		super(mensagem);
	}

	public RestaurantNotFoundException(Long restaurantId) {
		this(String.format("Não existe um cadastro de restaurante com código %d", restaurantId));
	}

}