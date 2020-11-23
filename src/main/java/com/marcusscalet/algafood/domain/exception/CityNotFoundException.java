package com.marcusscalet.algafood.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public CityNotFoundException(String mensagem) {
		super(mensagem);
	}

	public CityNotFoundException(Long cityId) {
		this(String.format("Não existe um cadastro de cidade com código %d", cityId));
	}

}