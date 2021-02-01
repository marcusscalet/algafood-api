package com.marcusscalet.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusOrder {

	CREATED("Criado"),
	CONFIRMED("Confirmado", CREATED),
	DELIVERED("Entregue", CONFIRMED),
	CANCELED("Cancelado", CREATED);

	private String description;
	private List<StatusOrder> previousStatus;
	
	StatusOrder(String description, StatusOrder... previousStatus) {
		this.description = description;
		this.previousStatus = Arrays.asList(previousStatus);
	}

	public String getDescription() {
		return this.description;
	}
	
	public boolean canNotBeChangedTo(StatusOrder newStatus) {
		return !newStatus.previousStatus.contains(this);
	}
}
