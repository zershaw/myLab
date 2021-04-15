package com.cbsys.saleexplore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The configuration for the swagger document. will only be configured in the developing mode
 */
@Profile("!prod")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //https://dzone.com/articles/spring-boot-2-restful-api-documentation-with-swagg

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.cbsys.saleexplore.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiEndPointsInfo());
    }
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Cluelez DiscountServer API")
                .contact(new Contact("Hector Xuguang Ren", "www.cluelez.net", "xuguang.ren@inceptioniai.org"))
                .version("1.0.1")
                .build();
    }
}