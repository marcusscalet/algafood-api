package com.marcusscalet.algafood.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	MESSAGE_NOT_READABLE("/message-not-redable", "Mensagem incompreensível"),
	ENTITY_NOT_FOUND("/entity-not-found", "Entidade não encontrada"),
	BUSINESS_ERROR("/business-error", "Violação de regra de negócio"),
	ENTITY_BEEN_USED("/entity-been-used", "Entidade em uso");
	
	private String title;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "https://algafood.com.br/" + path;
		this.title = title;
	}

}
