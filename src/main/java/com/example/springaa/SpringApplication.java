package com.example.springaa;

import com.example.springaa.repositories.JDBCQueueRepository;
import com.example.springaa.security.PasswordHasher;
import com.example.springaa.security.PasswordHasherImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
        PasswordHasherImpl p = context.getBean("getPasswordHasher", PasswordHasherImpl.class);
    }

}
