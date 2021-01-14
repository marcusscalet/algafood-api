package com.marcusscalet.algafood.domain.model;

public enum OrderStatus {

	CREATED("Criado"), ACCEPTED("Aceito"), DELIVERED("Entregue"), CANCELED("Cancelado");

	private String description;

	OrderStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
