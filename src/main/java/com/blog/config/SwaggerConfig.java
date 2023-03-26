package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {
    private static final String AUTHORIZATION_HEADER="Authorization";
    private ApiKey apiKey(){
        return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
    }
    private List<SecurityContext> securityContexts(){
        return Collections.singletonList(SecurityContext.builder().securityReferences(sf()).build());
    }
    private List<SecurityReference> sf(){
        AuthorizationScope authorizationScope=new AuthorizationScope("global","Access everything");
        return Collections.singletonList(new SecurityReference("JWT",new AuthorizationScope[]{authorizationScope}));
    }
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContexts())
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getInfo() {
        return new ApiInfo(
                "Blog Api Document",
                "This project is developed by Hakim",
                "1.0",
                "Terms of Service",
                new Contact("Hakim","hakim.com","hakim@gmail.com"),
                "License of Apis",
                "Api License URL",
                Collections.emptyList()
        );
    }
}
