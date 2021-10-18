package net.vpein.mail.api.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import net.vpein.mail.api.dto.MailRequest;
import net.vpein.mail.api.dto.MailResponse;
import net.vpein.mail.api.repo.TemplateRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Log
@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender sender;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private TemplateRepository templateRepository;

	@Autowired
	private Configuration config;
	
	public MailResponse sendEmail(MailRequest request) {

		String htmlMail = null;
		String textMail = null;

		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment
			// helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

			if (request.getTemplateHtmlId() != null ) {
				freemarker.template.Template htmlFreeTemplate = templateService.getFreemarkerTemplate(request.getTemplateHtmlId());
				htmlMail = FreeMarkerTemplateUtils.processTemplateIntoString(htmlFreeTemplate, request.getParameter());
			}
			if (request.getTemplateTextId() != null ) {
				freemarker.template.Template textFreeTemplate = templateService.getFreemarkerTemplate(request.getTemplateTextId());
				textMail = FreeMarkerTemplateUtils.processTemplateIntoString(textFreeTemplate, request.getParameter());
			}

			helper.setTo(request.getTo());
			helper.setFrom(request.getFrom());
			if (request.getCc() != null) helper.setCc(request.getCc());
			if (request.getBcc() != null) helper.setBcc(request.getBcc());
			helper.setSubject(request.getSubject());

			if (textMail != null) helper.setText(textMail, false);
			if (htmlMail != null ) helper.setText(htmlMail, true);

			//helper.addAttachment(String.format("attachment%L",Id);

			sender.send(message);

			response.setMessage("mail send to : " + request.getTo());
			response.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response;
	}
	

}
