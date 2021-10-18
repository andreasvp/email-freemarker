package net.vpein.mail.api.controller;

import net.vpein.mail.api.entity.RksTemplate;
import net.vpein.mail.api.service.TemplateService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Log
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @PostMapping(value = "/",   consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> createTemplate(@RequestBody RksTemplate template) {

        log.info("Http Call createTemplate");
        try {
            RksTemplate templ = templateService.save(template);
            return new ResponseEntity<Object>(templ, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}",   consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getTemplate(@PathVariable long id) {

        log.info("Http Call getTemplate");
        try {
            Optional<RksTemplate> templ = templateService.findById(id);

            return new ResponseEntity<Object>(templ, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/html/{id}",   consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getHtmlTemplate(@PathVariable long id) {

        log.info("Http Call getHtmlTemplate");
        try {
            Optional<RksTemplate> templ = templateService.findById(id);
            return new ResponseEntity<Object>(templ, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(value = "/list",   consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> listTemplate() {

        log.info("Http Call listTemplate");
        try {
            List<RksTemplate> listTemplates = templateService.findAll();
            return new ResponseEntity<Object>(listTemplates, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
