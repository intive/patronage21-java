package com.intive.patronative.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI patronativeOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Patronative API")
                .description("Users module")
                .version("v0.0.1"));
    }

}