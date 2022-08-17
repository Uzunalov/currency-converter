package com.umbrella.currencyconverter.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "basicAuth";
        final String bearerSchemeName = "apiKeyAuth";
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("basic"))
                                .addSecuritySchemes(bearerSchemeName,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.APIKEY).name("Authorization")
                                                .in(SecurityScheme.In.HEADER))
                )
                .security(List.of(new SecurityRequirement().addList(securitySchemeName).addList(bearerSchemeName)))
                .info(new Info().title("Currency Converter").version("v1"));
    }
}
