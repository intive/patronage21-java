package com.intive.patronative.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    private final static String TITLE = "Patronative's user module API";
    private static final String DESCRIPTION = "Its purpose is to provide a set of basic operations such as " +
            "retrieving, saving, and updating user-focused data.";

    @Bean
    public OpenAPI patronativeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(TITLE)
                        .description(DESCRIPTION)
                        .version("v0.0.1"));
    }

}