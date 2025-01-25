package com.final_test_sof3012.sof3022_ass_restful_api.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173","http://127.0.0.1:5500")
                .allowedMethods("POST","GET","PUT","DELETE","PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
