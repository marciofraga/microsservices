package br.gov.mapa.core.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.gov.mapa.core.model.dto.PedidoDTO;

public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	TemplateEngine templateEngine;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Override
	public void sendOrderConfirmationEmail(PedidoDTO obj, String titulo) {
		SimpleMailMessage sm = prepareSimpleMailMessage(obj, titulo);
		sendEmail(sm);
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(PedidoDTO obj, String titulo) {
		
		try {
			MimeMessage mm = prepareMimeMessage(obj, titulo);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj, titulo);
		}
	}
	
	protected MimeMessage prepareMimeMessage(PedidoDTO obj, String titulo) throws MessagingException {
		MimeMessage mm = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		
		mmh.setTo(obj.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject(titulo);
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mm;
	}

	protected SimpleMailMessage prepareSimpleMailMessage(PedidoDTO obj, String titulo) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getEmail());
		sm.setFrom(sender);
		sm.setSubject(titulo);
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	protected String htmlFromTemplatePedido(PedidoDTO obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process(obj.getTemplate(), context);
	}
	
	@Override
	public void sendNewPasswordEmail(PedidoDTO cliente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage prepareNewPasswordEmail(PedidoDTO cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
}
