package com.cbsys.saleexplore.compos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;



/*
 * The class for the Msg picker for multiple language support
 */
@Component
public class LocalMsgTranslator {

    private static ResourceBundleMessageSource messageSource;

    @Autowired
    LocalMsgTranslator(ResourceBundleMessageSource messageSource) {
        LocalMsgTranslator.messageSource = messageSource;
    }

    public static String toLocale(String msg) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msg, null, locale);
    }

}
