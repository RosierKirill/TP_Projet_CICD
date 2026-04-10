package com.stockms.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** OpenAPI / Swagger UI configuration. */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Stock Management API")
                        .version("1.0.0")
                        .description("Retail supermarket stock management — POC/MVP/V1")
                        .contact(new Contact().name("Stock-MS Team"))
                        .license(new License().name("MIT")));
    }
}
