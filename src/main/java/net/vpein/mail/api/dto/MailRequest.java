package net.vpein.mail.api.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MailRequest {

	private String from;
	private String to;
	private String cc;
	private String bcc;

	private String subject;

	// Which template to use
	private Long templateHtmlId;
	private Long templateTextId;
	private String template;

	// values for the templates
	private Map<String, Object> parameter;

	// Attachments
	private Long attachmentId;

	// includes
	private Long includeId;

}
