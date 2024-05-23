package com.app.marketstore.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                description = "API documentation for MarketStore E-commerce Web Application",
                title = "Online Store Application API",
                contact = @Contact(
                        name = "Jey",
                        email = "jey@gmail.com",
                        url = "https://www.github.com/jey"
                ),
                version = "1.0",
                license = @License(
                        name = "Apache License",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "Terms of Service"
        ),
        security = {
                    @SecurityRequirement(
                        name = "Bearer Authentication",
                        scopes = {"global"}
            )
        }
)

@SecurityScheme(
        name = "Bearer Authentication",
        description = "JWT Authentication",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
