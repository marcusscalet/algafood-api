package com.marcusscalet.algafood.domain.exception;

public class EntityBeenUsedException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public EntityBeenUsedException(String message) {
		super(message);
	}
}
