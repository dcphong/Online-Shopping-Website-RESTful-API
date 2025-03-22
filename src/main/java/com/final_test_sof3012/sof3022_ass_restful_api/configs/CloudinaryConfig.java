package com.final_test_sof3012.sof3022_ass_restful_api.configs;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${CLOUDINARY_URL}")
    private String cloudinaryUrl;

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(cloudinaryUrl);
    }

}
