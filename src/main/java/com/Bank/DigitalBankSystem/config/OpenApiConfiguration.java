package com.Bank.DigitalBankSystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Christos Roumeliotis");
        myContact.setEmail("roumeliotisch.b@gmail.com");

        Info information = new Info()
                .title("Digital Bank System API")
                .version("1.0")
                .description("This API exposes endpoints of simple digital bank.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(
                new Server().url("http://localhost:8080").description("Development"),
                new Server().url("http://16.171.0.141:8080").description("Live")
        ));
    }
}
