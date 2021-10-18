package net.vpein.mail.api.service;

import net.vpein.mail.api.entity.RksTemplate;
import net.vpein.mail.api.repo.TemplateRepository;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Log
@Service
public class TemplateService {
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private Configuration config;

    Template getFreemarkerTemplate(Long templateId) {
        log.info(String.format("getFreemarkerTemplate for %s", templateId));
        String textDbTemplate = templateRepository.getOne(templateId).getContent();

        String strTemplate = null;
        Template textFreeTemplate = null;
        try {
            strTemplate = URLDecoder.decode(textDbTemplate, StandardCharsets.UTF_8.toString());

            textFreeTemplate = new Template("templateName", new StringReader(strTemplate), config);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            log.severe(String.format("UnsupportedEncodingException for getFreemarkerTemplate %S", templateId));
        } catch (TemplateNotFoundException templateNotFoundException) {
            log.severe(String.format("TemplateNotFoundException for getFreemarkerTemplate %s", templateId));
        } catch (MalformedTemplateNameException malformedTemplateNameException) {
            log.severe(String.format("MalformedTemplateNameException for getFreemarkerTemplate %s", templateId));
        } catch (ParseException parseException) {
            log.severe(String.format("ParseException for getFreemarkerTemplate %s", templateId));
        } catch (IOException ioException) {
            log.severe(String.format("IOException for getFreemarkerTemplate %s", templateId));
        }
        return textFreeTemplate;

    }

    public List<RksTemplate> findAll(){
        return templateRepository.findAll();
    }

    public Optional<RksTemplate> findById(Long id){
        return templateRepository.findById(id);
    }

    public RksTemplate save(RksTemplate template){
        return templateRepository.save(template);
    }


}
