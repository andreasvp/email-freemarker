package net.vpein.mail.api.controller;

import net.vpein.mail.api.dto.MailRequest;
import net.vpein.mail.api.dto.MailResponse;
import net.vpein.mail.api.repo.TemplateRepository;
import net.vpein.mail.api.service.EmailService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class MailController {

    @Autowired
    private EmailService service;

    @Autowired
    private TemplateRepository templateRepository;

    @PostMapping(value = "/sendEmail",   consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> sendEmail(@RequestBody MailRequest mailRequest) {

        MailResponse mailResponse;
        try {
            mailResponse = service.sendEmail(mailRequest);
            return new ResponseEntity<Object>(mailResponse, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }



}
