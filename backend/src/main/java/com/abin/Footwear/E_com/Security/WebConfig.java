package com.abin.Footwear.E_com.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String projectPath = System.getProperty("user.dir");
        String imagePath = "file:///" + projectPath + "/images/";
        imagePath = imagePath.replace("\\", "/"); 

        registry.addResourceHandler("/images/**")
                .addResourceLocations(imagePath);

        System.out.println("Serving images from: " + imagePath);
    }
}

