package com.marcusscalet.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	MESSAGE_NOT_READABLE("message-not-redable", "Mensagem incompreensível"),
	RESOURCE_NOT_FOUND("resource-not-found", "Recurso não encontrado"),
	BUSINESS_ERROR("business-error", "Violação de regra de negócio"),
	ENTITY_BEING_USED("entity-being-used", "Entidade em uso"),
	INVALID_PARAMETER("invalid-parameter","Parâmetro inválido"),
	SYSTEM_ERROR("system-error", "Erro de sistema"),
	INVALID_DATA("invalid-data", "Dados inválidos");
	
	private String title;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "https://algafood.com.br/" + path;
		this.title = title;
	}

}
