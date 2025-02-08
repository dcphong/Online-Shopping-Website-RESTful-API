package com.final_test_sof3012.sof3022_ass_restful_api.configs;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class JwtConfig {

    @Value("${JWT_SECRET_KEY}")
    String SECRET_KEY;

    @Bean
    public Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    
}
