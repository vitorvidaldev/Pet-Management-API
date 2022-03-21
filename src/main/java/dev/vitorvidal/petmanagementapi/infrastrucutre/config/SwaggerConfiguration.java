package dev.vitorvidal.petmanagementapi.infrastrucutre.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {
    @Bean
    public OpenAPI swaggerConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pet Management API")
                        .description("Pet Management Service")
                        .contact(new Contact().name("Vitor Vidal"))
                        .version("v2.0")
                        .license(new License().name("MIT License")))
                .components(new Components().addSecuritySchemes("bearer-jwt",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER).name("Authorization")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt", List.of("collaborator")));
    }
}
