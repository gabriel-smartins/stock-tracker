package com.gmartins.stocktracker.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
public class OpenAPIConfig {

    @Bean
    OpenAPI myOpenAAPI() {
        Contact contact = new Contact();
        contact.setEmail("gab.smartinn@outlook.com");
        contact.setName("Gabriel Martins");
        contact.setUrl("https://www.linkedin.com/in/gabriel-martins-2588b7291/");

        Info info = new Info()
                .title("Stock Tracker API")
                .version("1.0")
                .contact(contact)
                .description("API to management of stock buy on stock exchange");

        Server localServer = new Server();
        localServer.setUrl("https://localhost:8080");
        localServer.setDescription("Server URL local environment");

        Server devServer = new Server();
        devServer.setUrl("https://localhost:8080");
        devServer.setDescription("Server URL dev environment");

        return new OpenAPI().info(info).servers(List.of(localServer, devServer));

    }
}
