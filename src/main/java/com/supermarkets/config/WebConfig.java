package com.supermarkets.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.supermarkets")
public class WebConfig implements WebMvcConfigurer {

	/*
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 *
	 * registry.addInterceptor(new LoggerInterceptor()); }
	 */
	
	@Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(2097152); // 2MB
        return multipartResolver;
    }
	@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        //objectMapper.enable(SerializationFeature.);
        return objectMapper;
    }
}
