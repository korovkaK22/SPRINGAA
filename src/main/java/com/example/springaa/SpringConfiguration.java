package com.example.springaa;


import com.example.springaa.repositories.UserRepository;
import com.example.springaa.services.AuthorizationService;
import com.example.springaa.services.QueueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class SpringConfiguration {
    @Bean
    public QueueService getQueueService(){
        return new QueueService();
    }

    @Bean
    public AuthorizationService getAuthorizationService(UserRepository p){
        return new AuthorizationService(p);
    }

    @Bean
    public DataSource dataSource(@Value("${spring.datasource.url}") String url,
                                 @Value("${spring.datasource.driver-class-name}") String driverClassName,
                                 @Value("${spring.datasource.username}") String username,
                                 @Value("${spring.datasource.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}
