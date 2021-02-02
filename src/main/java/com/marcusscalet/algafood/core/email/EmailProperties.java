package com.marcusscalet.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

	private MailType mailType = MailType.FAKE;

	@NotNull
	private String sender;

	private Sandbox sandbox = new Sandbox();

	public enum MailType {
		SMTP, FAKE, SANDBOX
	}

	@Getter
	@Setter
	public class Sandbox {
		private String recipient;
	}
}
