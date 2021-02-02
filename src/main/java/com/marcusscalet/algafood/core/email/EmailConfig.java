package com.marcusscalet.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.marcusscalet.algafood.domain.service.SendEmailService;
import com.marcusscalet.algafood.infrastructure.service.email.FakeEmailService;
import com.marcusscalet.algafood.infrastructure.service.email.SandboxMailService;
import com.marcusscalet.algafood.infrastructure.service.email.SmtpSendEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;

	@Bean
	public SendEmailService sendEmailService() {
		switch (emailProperties.getMailType()) {
		case FAKE: {
			return new FakeEmailService();
		}
		case SMTP: {
			return new SmtpSendEmailService();
		}
		case SANDBOX: {
			return new SandboxMailService();
		}
		default:
			return null;
		}
	}
}
