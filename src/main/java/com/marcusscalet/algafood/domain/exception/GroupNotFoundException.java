package com.marcusscalet.algafood.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public GroupNotFoundException(String mensagem) {
		super(mensagem);
	}

	public GroupNotFoundException(Long codigoId) {
		this(String.format("Não existe um grupo com código %d", codigoId));
	}

}