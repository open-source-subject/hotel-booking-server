package com.bookinghotel.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String API_KEY = "Bearer Token";

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI().info(
                new Info()
                        .title("Booking Hotel API")
                        .version("1.0")
                        .description("Documentation Booking Hotel API v1.0")
        );

        openAPI.components(
                new Components()
                        .addSecuritySchemes(
                                API_KEY,
                                new SecurityScheme()
                                        .name("Authorization")
                                        .scheme("Bearer")
                                        .bearerFormat("JWT")
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                        )
        );
        openAPI.addSecurityItem(new SecurityRequirement().addList(API_KEY));

        return openAPI;
    }
}