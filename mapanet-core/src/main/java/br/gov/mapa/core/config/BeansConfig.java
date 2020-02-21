package br.gov.mapa.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.gov.mapa.core.services.EmailService;
import br.gov.mapa.core.services.SmtpEmailService;


@Configuration
public class BeansConfig {

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
