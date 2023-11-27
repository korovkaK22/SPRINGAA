package com.example.springaa;


import com.example.springaa.repositories.UserRepository;
import com.example.springaa.services.AuthorizationService;
import com.example.springaa.services.QueueService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class SpringConfiguration {
    @Bean
    public QueueService getQueueService(){
        return new QueueService();
    }


    @Bean
    public AuthorizationService getAuthorizationService(UserRepository p){
        return new AuthorizationService(p);
    }

//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/jsp/");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }

}
