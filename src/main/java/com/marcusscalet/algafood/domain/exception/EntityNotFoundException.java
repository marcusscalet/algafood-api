package com.marcusscalet.algafood.domain.exception;

public abstract class EntityNotFoundException extends GenericException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String message) {
		super(message);
	}

}
