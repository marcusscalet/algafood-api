package com.marcusscalet.algafood.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface SendEmailService {

	void send(Message message);
	
	@Getter
	@Builder
	class Message{
		
		@Singular
		private Set<String> recipients;
		
		@NonNull
		private String topic;
		
		@NonNull
		private String body;
		
		@Singular
		private Map<String, Object> variables;
	}
}
