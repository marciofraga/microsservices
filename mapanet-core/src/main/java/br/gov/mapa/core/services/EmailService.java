package br.gov.mapa.core.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import br.gov.mapa.core.model.dto.PedidoDTO;

@Component
public interface EmailService {

	void sendOrderConfirmationEmail(PedidoDTO obj, String titulo);
	
	void sendOrderConfirmationHtmlEmail(PedidoDTO obj, String titulo);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordEmail(PedidoDTO obj, String newPass);
}
