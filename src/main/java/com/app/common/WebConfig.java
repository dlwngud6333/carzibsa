package com.app.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/rest/**")
        .allowedOrigins("http://localhost:7777")
        .allowedMethods("GET","POST","OPTIONS","PUT","DELETE")
        .allowedHeaders("header1","header2","header3")
        .exposedHeaders("header1", "header2")
        .allowCredentials(true).maxAge(3600);
		
    }
}
