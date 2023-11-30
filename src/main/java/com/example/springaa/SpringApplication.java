package com.example.springaa;

import com.example.springaa.entity.User;
import com.example.springaa.repositories.UserRepository;
import com.example.springaa.security.PasswordHasherImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URI;
import java.util.Optional;


@OpenAPIDefinition(
        info = @Info(
                title = "Online queries",
                contact = @Contact(
                        name = "Sereda Andrii",
                        email = "email@gmail.com"
                ),
                description = "MyOpen",
                version = "2.2.0"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "test server"),
                @Server(url = "http://example.com", description = "production server")
        }
)
@SpringBootApplication
public class SpringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
         PasswordHasherImpl p = context.getBean("getPasswordHasher", PasswordHasherImpl.class);
        }

}
