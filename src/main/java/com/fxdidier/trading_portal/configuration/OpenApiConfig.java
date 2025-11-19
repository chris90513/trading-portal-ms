package com.fxdidier.trading_portal.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Trading Portal API",
                version = "v1",
                description = "Backend para portal de bitácora y analíticas de trading."
        ),
        // 🔐 Aplica seguridad bearerAuth por defecto a todos los endpoints
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",           // nombre que referenciaremos en @SecurityRequirement
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"           // solo informativo, pero está bien ponerlo
)
public class OpenApiConfig {
    // No hace falta código dentro, las anotaciones hacen el trabajo
}