package com.deped.config;

import com.deped.model.config.server.ServerEnumKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"com.deped.config", "com.deped.restcontroller", "com.deped.service", "com.deped.repository"})
@EnableWebMvc
public class SpringMVCRestConfiguration extends WebMvcConfigurerAdapter {

    private static final String RESOURCES_URI = "/public/**";
    private static final int MAX_UPLOAD_SIZE_IN_MB = 5 * 1024 * 1024; // 5 MB

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Map<ServerEnumKey, String> map = SharedConfigData.getAppConfigs(false);
        registry.addResourceHandler(RESOURCES_URI).addResourceLocations("file:" + map.get(ServerEnumKey.RESOURCE_PATH_ON_DISK));
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver();
        cmr.setMaxUploadSize(MAX_UPLOAD_SIZE_IN_MB * 2);
        cmr.setMaxUploadSizePerFile(MAX_UPLOAD_SIZE_IN_MB);
        return cmr;
    }

//    @Bean
//    public MappingJackson2HttpMessageConverter getMappingJacksonConverter(){
//        return new MappingJackson2HttpMessageConverter(new HibernateAwareObjectMapper());
//    }

}
