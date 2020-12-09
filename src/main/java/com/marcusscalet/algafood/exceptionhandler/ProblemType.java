package com.marcusscalet.algafood.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	MESSAGE_NOT_READABLE("/message-not-redable", "Mensagem incompreensível"),
	RESOURCE_NOT_FOUND("/resource-not-found", "Recurso não encontrado"),
	BUSINESS_ERROR("/business-error", "Violação de regra de negócio"),
	ENTITY_BEEN_USED("/entity-been-used", "Entidade em uso"),
	INVALID_PARAMETER("/invalid-parameter","Parâmetro inválido"),
	SYSTEM_ERROR("/system-error", "Erro de sistema");
	
	private String title;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "https://algafood.com.br/" + path;
		this.title = title;
	}

}
