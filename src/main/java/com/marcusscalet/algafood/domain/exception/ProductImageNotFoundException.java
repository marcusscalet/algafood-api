package com.marcusscalet.algafood.domain.exception;

public class ProductImageNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public ProductImageNotFoundException(String message) {
		super(message);
	}

	public ProductImageNotFoundException(Long productId, Long restaurantId) {
		this(String.format("Não existe um cadastro de imagem com código %d", productId, restaurantId));
	}

}
