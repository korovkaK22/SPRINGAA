package com.example.springaa;


import com.example.springaa.repositories.UserRepository;
import com.example.springaa.security.PasswordHasher;
import com.example.springaa.security.PasswordHasherImpl;
import com.example.springaa.services.AuthorizationService;
import com.example.springaa.services.QueueService;
import com.example.springaa.util.QueuesAccessValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@PropertySource("classpath:application.properties")
public class SpringConfiguration {

    @Bean
    @Scope("singleton")
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public QueuesAccessValidation getQueueChangingValidation(QueueService queueService){
        return new QueuesAccessValidation(queueService);
    }

    @Bean
    @Scope("prototype")
    public PasswordHasher getPasswordHasher(){
        return new PasswordHasherImpl();
    }


    @Bean
    public DataSource dataSource(Environment environment) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

}
