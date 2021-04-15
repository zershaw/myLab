package com.cbsys.saleexplore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/*
 * The class for the configuration of multiple language support.
 * The locale should be set in the header as "Accept-Language"
 * https://blog.usejournal.com/spring-boot-rest-internationalization-9ab3fce2489
 */
@Configuration
public class LocaleResolverConfig extends AcceptHeaderLocaleResolver
        implements WebMvcConfigurer {

    // the supported languages, each one should have a corresponding message property in resources
    List<Locale> LOCALES = Arrays.asList(Locale.ENGLISH, Locale.CHINA);

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }

}