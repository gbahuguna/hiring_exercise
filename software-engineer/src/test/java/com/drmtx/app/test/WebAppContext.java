package com.drmtx.app.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
 
import java.util.Properties;
 
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.drmtx.app"
})
public class WebAppContext extends WebMvcConfigurerAdapter {
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
 
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
 
    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
 
        Properties exceptionMappings = new Properties();
 
        exceptionMappings.put("com.drmtx.app.exception.CommentAnalysisException", "error/404");
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");
 
        exceptionResolver.setExceptionMappings(exceptionMappings);
 
        Properties statusCodes = new Properties();
 
        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");
 
        exceptionResolver.setStatusCodes(statusCodes);
 
        return exceptionResolver;
    }
 
 
}