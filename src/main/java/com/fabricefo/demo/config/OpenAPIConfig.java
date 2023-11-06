package com.fabricefo.demo.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {
    
    @Value("$(fabricefo.openapi.dev-url)")
    private String devUrl;

    @Value("$(fabricefo.openapi.prod-url)")
    private String prodUrl;


    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(devUrl);
        prodServer.setDescription("Server URL in Production environment");    
        
        Contact contact = new Contact();
        contact.setEmail("ffourel@gmail.com");
        contact.setName("Fabrice Fourel");
        contact.setUrl("https://www.fabricefourel.com");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
            .title("Tutorial Management API")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints to manage tutorials.").termsOfService("https://www.fabricefourel.com/terms")
            .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));    
    }
    
}
