package com.example.springbootresti.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class RestConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200",
                                "https://angular-springboot-restful.herokuapp.com/**")
                        .allowedMethods("GET","POST","DELETE","PUT")
                        .allowedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Headers",
                                "Authorization","Content-Type","Origin",
                                "Accept","X-Requested-With","Access-Control-Request-Method"
                                ,"Access-Control-Request-Headers");
            }
        };
    }
}
