package com.eliasfs06.spring.restapi.stock.manager.service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String messageCode){
        try {
            return messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}