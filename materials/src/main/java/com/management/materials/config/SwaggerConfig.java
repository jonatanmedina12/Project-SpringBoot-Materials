package com.management.materials.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para documentación de la API
 */
@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server().url("http://localhost:" + serverPort).description("Servidor de desarrollo"),
                        new Server().url("https://api.materiales.com").description("Servidor de producción")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Token JWT obtenido del servicio de autenticación")
                        )
                );
    }

    private Info apiInfo() {
        return new Info()
                .title("API de Gestión de Materiales")
                .description("Sistema RESTful para la administración de materiales, ciudades y departamentos. " +
                        "Esta API permite realizar operaciones CRUD sobre materiales, así como búsquedas " +
                        "avanzadas por diferentes criterios.")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Equipo de Desarrollo")
                        .email("desarrollo@materiales.com")
                        .url("https://materiales.com"))
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT"));
    }
}