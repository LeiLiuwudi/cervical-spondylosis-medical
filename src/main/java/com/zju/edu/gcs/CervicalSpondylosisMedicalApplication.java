package com.zju.edu.gcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class CervicalSpondylosisMedicalApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(CervicalSpondylosisMedicalApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowedHeaders("*");
    }
}
