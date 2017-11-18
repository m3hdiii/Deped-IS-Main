package com.deped.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan(basePackages = {"com.deped.config", "com.deped.controller", "com.deped.security"})
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
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver();
        cmr.setMaxUploadSize(MAX_UPLOAD_SIZE_IN_MB * 2);
        cmr.setMaxUploadSizePerFile(MAX_UPLOAD_SIZE_IN_MB);
        return cmr;

    }

    /*
    TODO further consideration
    @Bean(name = "messageSource")
    public ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("validation/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;

    }*/
}
