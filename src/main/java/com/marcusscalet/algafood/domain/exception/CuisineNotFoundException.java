package com.marcusscalet.algafood.domain.exception;

public class CuisineNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public CuisineNotFoundException(String mensagem) {
		super(mensagem);
	}

	public CuisineNotFoundException(Long cuisineId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", cuisineId));
	}

}