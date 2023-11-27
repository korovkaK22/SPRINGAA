package com.example.springaa;

import com.example.springaa.repositories.JDBCQueueRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =  org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
        JDBCQueueRepository rep =  context.getBean("JDBCQueueRepository", JDBCQueueRepository.class);
        System.out.println();
    }

}
