package com.deped.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = {"com.deped.config", "com.deped.restcontroller", "com.deped.service", "com.deped.repository"})
@EnableWebMvc
public class SpringMVCConfiguration extends WebMvcConfigurerAdapter {

    private static final String RESOURCES_URI = "/public/**";
    private static final String RESOURCES_MAPPING = "/resources/assets/";
    private static final int MAX_UPLOAD_SIZE_IN_MB = 5 * 1024 * 1024; // 5 MB


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCES_URI).addResourceLocations(RESOURCES_MAPPING);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver();
        cmr.setMaxUploadSize(MAX_UPLOAD_SIZE_IN_MB * 2);
        cmr.setMaxUploadSizePerFile(MAX_UPLOAD_SIZE_IN_MB);
        return cmr;

    }

}
