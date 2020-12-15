package com.marcusscalet.algafood.domain.exception;

public class EntityBeingUsedException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public EntityBeingUsedException(String message) {
		super(message);
	}
}
