package com.romy.gestao_vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;


@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Gestão de Vagas API")
                        .version("1.0.0")
                        .description("API para gestão de vagas de estacionamento")
                        )
                        .schemaRequirement("jwt_auth", securityScheme())
                        ;
                }


    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name("jwt_auth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}
