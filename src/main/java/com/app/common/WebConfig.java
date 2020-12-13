package com.app.common;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	/**
	 * 보안 CORS 허용 URL
	 */
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/rest/**")
        .allowedOrigins("http://localhost:7777","https://nid.naver.com")
        .allowedMethods("GET","POST","OPTIONS","PUT","DELETE")
        .allowedHeaders("header1","header2","header3")
        .exposedHeaders("header1", "header2")
        .allowCredentials(true).maxAge(3600);
		
    }
	
    @Bean
    public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
        FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new XssEscapeServletFilter());
        filterRegistration.setOrder(1);
        filterRegistration.addUrlPatterns("/*");

        return filterRegistration;
    }
	
}
