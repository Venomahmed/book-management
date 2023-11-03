package com.ness.bookmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {

//        SecurityScheme securityScheme = new HttpAuthenticationScheme("JWT",
//                "Bearer Token Authentication",
//                "http",
//                "bearer",
//                "JWT",
//                new ArrayList<>());

        SecurityScheme securityScheme = new ApiKey("JWT",
                "http",
                "bearer");


        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ness.bookmanagement.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(List.of(securityScheme))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Book Management API")
                .description("API documentation Book Management System")
                .version("1.0")
                .build();
    }

}

