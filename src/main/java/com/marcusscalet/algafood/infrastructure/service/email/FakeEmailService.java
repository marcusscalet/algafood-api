package com.marcusscalet.algafood.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEmailService extends SmtpSendEmailService {
	
	@Override
	public void send(Message message) {
		
		String body = processTemplate(message);
		
		log.info("[FAKE E-MAIL] Para: {}\n{}", message.getRecipients(), body);
	}
}
