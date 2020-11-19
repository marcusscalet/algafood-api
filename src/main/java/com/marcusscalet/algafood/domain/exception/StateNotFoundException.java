package com.marcusscalet.algafood.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public StateNotFoundException(String mensagem) {
		super(mensagem);
	}

	public StateNotFoundException(Long estadoId) {
		this(String.format("Não existe um cadastro de estado com código %d", estadoId));
	}

}