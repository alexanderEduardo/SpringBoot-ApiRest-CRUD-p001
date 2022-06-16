package com.example.springbootresti.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringbootRestIApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRestIApplication.class, args);
    }

    /*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                 .allowedOrigins("http://localhost:4200","https://angular-springboot-restful.herokuapp.com")
                 .allowedMethods("GET", "POST", "PUT", "DELETE")
                 .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin"
                         ,"Access-Control-Request-Method","Access-Control-Request-Headers","Access-Control-Allow-Origin")
                 .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                        .allowCredentials(true)
                        .maxAge(3600);
                 }

                 };
    }*/

}
