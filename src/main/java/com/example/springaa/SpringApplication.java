package com.example.springaa;

import com.example.springaa.entity.User;
import com.example.springaa.repositories.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URI;
import java.util.Optional;

@SpringBootApplication
public class SpringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
        // PasswordHasherImpl p = context.getBean("getPasswordHasher", PasswordHasherImpl.class);
        }

}
